using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;
using MySql.Data.MySqlClient;

//database:test

namespace 数据库
{
    class Program
    {
        static void Main(string[] args)
        {
            mytest a = new mytest();
            a.connetmysql();
            a.writer();
            a.reader();
            a.closemysql();
        }
    }

    public class mytest
    {
        MySqlConnection sql = null;
        math map = null;
        MyTree mytree = new MyTree();

        public void connetmysql(){
            String connetStr = "server=127.0.0.1;port=3306;user=root;password=123; database=数据库;";
            // server=127.0.0.1/localhost 代表本机，端口号port默认是3306可以不写
            sql = new MySqlConnection(connetStr);
            try
            {
                sql.Open();//打开通道，建立连接，可能出现异常,使用try catch语句
                Console.WriteLine("已经建立连接");
            }
            catch (MySqlException ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void closemysql(){
            sql.Close();
            sql.Dispose();
        }
        public void writer()//写到数据库
        {
            try
            {
                using (StreamReader sr = new StreamReader("ex.json"))//反序列化得到对象
                {
                    var str = sr.ReadToEnd();
                    sr.Close();
                    map = Deserialize<math>(str);
                }
            }
            catch (IOException)
            {
                Console.WriteLine("请检查运行目录是否存在ex.json，或者是否被其他程序占用。", "文件打开错误");
                return;
            }
            int i, j = 0, z;
            foreach (province a in map.provinces)
            {
                string index;
                MySqlCommand command;
                j++;
                index = "insert into map values(" + j + ",'" + a.code + "','" + a.name + "',null)";//SQL语句
                command = new MySqlCommand(index, sql);
                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException)
                {
                    Console.WriteLine("节点已存在!\n请删除后再试。节点信息:code=" + a.code + ",id=" + j, "错误");
                    return;
                }
                z = j;
                foreach (area b in a.areas)
                {
                    j++;
                    index = "insert into map values(" + j + ",'" + b.code + "','" + b.name + "'," + z + ")";//SQL语句
                    command = new MySqlCommand(index, sql);
                    try
                    {
                        command.ExecuteNonQuery();
                    }
                    catch (MySqlException)
                    {
                        Console.WriteLine("节点已存在!\n请删除后再试。节点信息:code=" + b.code + ",id=" + z, "错误");
                        return;
                    }
                    i = j;
                    foreach (citie c in b.cities)
                    {
                        j++;
                        index = "insert into map values(" + j + ",'" + c.code + "','" + c.name + "'," + i + ")";//SQL语句
                        command = new MySqlCommand(index, sql);
                        try
                        {
                            command.ExecuteNonQuery();
                        }
                        catch (MySqlException)
                        {
                            Console.WriteLine("节点已存在!\n请删除后再试。节点信息:code=" + c.code + ",id=" + i, "错误");
                            return;
                        }
                    }
                }
            }
            Console.WriteLine("写到数据库成功!");
        }

        public void reader()
        {
            bool zx = true;
            MySqlCommand cmd = new MySqlCommand("select * from map", sql);//创建查询对象
            MySqlDataReader reader = cmd.ExecuteReader();//查询
            while (reader.Read())//指针推进
            {
                Object[] values = new Object[4];//创建数组容器，因为类型未知所以是所有类的父类object类
                reader.GetValues(values);//获取当前每个元素放到数组
                mytree.add(values);//添加一个节点
                zx = false;
                //Console.WriteLine("{0},{1},{2},{3}",values[0],values[1], values[2],values[3]);//id、code、name、parent_id
            }
            if (zx) Console.WriteLine("数据库中没有数据可以处理！", "错误");
            mytree.Output();
            Console.WriteLine("读取成功写到运行目录的ex.xml文件中请查看!");
        }

        private void writerxml(MyTreeNode a)
        {
            if (a.nextnode.Count == 0) return;
            foreach (MyTreeNode b in a.nextnode)
            {
                writerxml(b);
            }
        }

        private T Deserialize<T>(string jsonText)
        {
            JsonSerializer json = new JsonSerializer();
            json.NullValueHandling = NullValueHandling.Ignore;
            json.ObjectCreationHandling = ObjectCreationHandling.Replace;
            json.MissingMemberHandling = MissingMemberHandling.Ignore;
            json.ReferenceLoopHandling = ReferenceLoopHandling.Ignore;
            StringReader sr = new StringReader(jsonText);
            JsonTextReader reader = new JsonTextReader(sr);
            var result = json.Deserialize<T>(reader);
            reader.Close();
            sr.Close();
            return result;
        }//反序列化
    }

    class MyTree
    {
        public MyTreeNode head = new MyTreeNode();
        XmlElement sch, provinces, cities, areas;
        XmlDocument doc;
        public void add(Object[] values)
        {
            MyTreeNode newnode = new MyTreeNode(values);
            if (values[3].ToString() == "")//创建父节点
            {
                head.nextnode.Add(newnode);
            }
            else//其他子节点
            {
                MyTreeNode a = MyFind(head, Convert.ToInt32(values[3]));//根据父id找父节点用递归方法遍历
                a.nextnode.Add(newnode);//将孩子放进去
            }
        }

        public void Output()
        {
            if (head.nextnode.Count == 0) return;//树为空
                                                 //创建xml读写对象
            doc = new XmlDocument();
            //创建根节点用于说明编码符号等必要信息
            XmlDeclaration xmldecl = doc.CreateXmlDeclaration("1.0", "utf-8", null);//比较特殊的节点类
                                                                                    //获取文档头部
            XmlElement root = doc.DocumentElement;
            //将根节点插入头部
            doc.InsertBefore(xmldecl, root);
            //创建一个自己的节点
            sch = doc.CreateElement("map");
            doc.AppendChild(sch);//将自己的节点插入到xml文档
            foreach (MyTreeNode b in head.nextnode)
            {
                writerxml(b);
            }
            StreamWriter sw = new StreamWriter("ex.xml", false, new UTF8Encoding(false));
            doc.Save(sw);
            sw.Close();
        }

        private void writerxml(MyTreeNode a)
        {
            if (a.code.Length == 2)//省节点
            {
                provinces = doc.CreateElement("provinces");
                provinces.SetAttribute("code", a.code);
                provinces.SetAttribute("name", a.name);
                sch.AppendChild(provinces);//将节点插入这个的孩子节点
            }
            else if (a.code.Length == 4)//市节点
            {
                cities = doc.CreateElement("city");
                cities.SetAttribute("code", a.code);
                cities.SetAttribute("name", a.name);
                provinces.AppendChild(cities);
            }
            else if (a.code.Length == 6)//区节点
            {
                areas = doc.CreateElement("area");
                areas.SetAttribute("code", a.code);
                areas.SetAttribute("name", a.name);
                cities.AppendChild(areas);
            }
            //Console.WriteLine("{0},{1},{2}", a.name, a.code, a.id);//输出
            if (a.nextnode.Count == 0) return;//最后的节点
            foreach (MyTreeNode b in a.nextnode)//递归子节点
            {
                writerxml(b);
            }
        }

        private MyTreeNode MyFind(MyTreeNode a, int id)//递归查找
        {
            if (a.id == id)
                return a;
            else if (a.nextnode.Count == 0)
                return null;
            else
            {
                foreach (MyTreeNode b in a.nextnode)
                {
                    MyTreeNode c = MyFind(b, id);
                    if (c != null) return c;
                }
                return null;

            }
        }
    }

    class MyTreeNode
    {
        public int id { get; set; }
        public string name { get; set; }
        public string code { get; set; }
        public List<MyTreeNode> nextnode { get; set; }

        public MyTreeNode()
        {//创建空节点是调用，就是所有的节点的父节点
            nextnode = new List<MyTreeNode>();
        }
        public MyTreeNode(object[] values)
        {//创建其他节点用
            nextnode = new List<MyTreeNode>();
            id = Convert.ToInt32(values[0]);
            code = values[1].ToString();
            name = values[2].ToString();
        }
    }

    class math
    {
        public List<province> provinces { get; set; }
    }//反序列化对象


    class province//省
    {
        public string name { get; set; }
        public string code { get; set; }
        public List<area> areas { get; set; }
    }

    class area//市
    {
        public string name { get; set; }
        public string code { get; set; }
        public List<citie> cities { get; set; }
    }

    class citie//区
    {
        public string name { get; set; }
        public string code { get; set; }
    }
}
