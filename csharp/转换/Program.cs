using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;

namespace 转换
{
    class Program
    {
        StreamReader provinces, areas, cities;//三个文件对象

        static void Main(string[] args)
        {
            Program a = new Program();
            a.openfile();
            a.xml();
            a.json();
            a.closefile();
        }


        private void xml()
        {
            //回到文件头
            provinces.BaseStream.Seek(0, SeekOrigin.Begin);
            areas.BaseStream.Seek(0, SeekOrigin.Begin);
            cities.BaseStream.Seek(0, SeekOrigin.Begin);
            //创建xml读写对象
            XmlDocument doc = new XmlDocument();
            //创建根节点
            XmlDeclaration xmldecl = doc.CreateXmlDeclaration("1.0", "utf-8", null);
            XmlElement root = doc.DocumentElement;
            doc.InsertBefore(xmldecl, root);
            //添加一个分类
            var sch = doc.CreateElement("map");
            doc.AppendChild(sch);
            string provincesline, citiesline, areasline;//三个文件的当前行
            string[] provincestext, citiestext = null, areastext = null;//三行解析出来的文本 1为地点 0总是等于2
            provinces.ReadLine();//去除code等标题
            cities.ReadLine();
            areas.ReadLine();
            provincesline = provinces.ReadLine();//先读一行
            citiesline = cities.ReadLine();
            areasline = areas.ReadLine();
            if (citiesline != null) citiestext = citiesline.Split(',');//读后处理
            if (areasline != null) areastext = areasline.Split(',');
            do//省节点
            {
                provincestext = provincesline.Split(',');
                var a = doc.CreateElement("provinces");
                a.SetAttribute("code", provincestext[0]);
                a.SetAttribute("name", provincestext[1].Trim('"'));
                sch.AppendChild(a);
                do//市节点
                {
                    var b = doc.CreateElement("cities");
                    b.SetAttribute("code", citiestext[0]);
                    b.SetAttribute("name", citiestext[1].Trim('"'));
                    a.AppendChild(b);
                    do//区
                    {
                        var c = doc.CreateElement("areas");
                        c.SetAttribute("code", areastext[0]);
                        c.SetAttribute("name",areastext[1].Trim('"'));
                        b.AppendChild(c);
                        areasline = areas.ReadLine();
                        if (areasline != null) areastext = areasline.Split(',');
                    } while (areasline != null & citiestext[0].Equals(areastext[2]));
                    citiesline = cities.ReadLine();
                    if (citiesline != null) citiestext = citiesline.Split(',');
                } while (citiesline != null & provincestext[0].Equals(citiestext[2]));
            } while ((provincesline = provinces.ReadLine()) != null);
            StreamWriter sw = new StreamWriter("ex.xml", false, new UTF8Encoding(false));
            doc.Save(sw);
            sw.Close();
        }

        private void json()
        {
            //回到文件头
            provinces.BaseStream.Seek(0, SeekOrigin.Begin);
            areas.BaseStream.Seek(0, SeekOrigin.Begin);
            cities.BaseStream.Seek(0, SeekOrigin.Begin);
            math ma = new math();
            ma.provinces = new List<province>();

            string provincesline, citiesline, areasline;//三个文件的当前行
            string[] provincestext, citiestext = null, areastext = null;//三行解析出来的文本 1为地点 0总是等于2
            provinces.ReadLine();//去除code等标题
            cities.ReadLine();
            areas.ReadLine();
            provincesline = provinces.ReadLine();//先读一行
            citiesline = cities.ReadLine();
            areasline = areas.ReadLine();
            if (citiesline != null) citiestext = citiesline.Split(',');//读后处理
            if (areasline != null) areastext = areasline.Split(',');
            do//省节点
            {
                provincestext = provincesline.Split(',');
                var a = new province();
                a.code = provincestext[0];
                a.name = provincestext[1].Trim('"');
                a.areas = new List<area>();
                do//市节点
                {
                    var b = new area();
                    b.code = citiestext[0];
                    b.name = citiestext[1].Trim('"');
                    a.areas.Add(b);
                    b.cities = new List<citie>();
                    do//区
                    {
                        var c = new citie();
                        c.code = areastext[0];
                        c.name = areastext[1].Trim('"');
                        b.cities.Add(c);
                        areasline = areas.ReadLine();
                        if (areasline != null) areastext = areasline.Split(',');
                    } while (areasline != null & citiestext[0].Equals(areastext[2]));
                    citiesline = cities.ReadLine();
                    if (citiesline != null) citiestext = citiesline.Split(',');
                } while (citiesline != null & provincestext[0].Equals(citiestext[2]));
                ma.provinces.Add(a);
            } while ((provincesline = provinces.ReadLine()) != null);
            var str = Serialize<math>(ma);
            StreamWriter st = new StreamWriter("ex.json", false, new UTF8Encoding(false));
            st.Write(str);
            st.Close();
            st.Dispose();
        }

        private string Serialize<T>(T obj)
        {
            IsoDateTimeConverter timeConverter = new IsoDateTimeConverter();
            //使用自定义日期格式，默认是ISO8601格式     
            timeConverter.DateTimeFormat = "yyyy-MM-dd HH:mm:ss";

            return JsonConvert.SerializeObject(obj, Newtonsoft.Json.Formatting.None, timeConverter);
        }


        private void openfile()//打开文件
        {
            try
            {
                provinces = new StreamReader("provinces.csv");
            }
            catch (FileNotFoundException r)
            {
                Console.WriteLine(r.ToString());
                return;
            }
            try
            {
                cities = new StreamReader("cities.csv");
            }
            catch (FileNotFoundException r)
            {
                Console.WriteLine(r.ToString());
                provinces.Close();//申请失败清理前面成功打开的文件
                provinces.Dispose();
                return;
            }
            try
            {
                areas = new StreamReader("areas.csv");
            }
            catch (FileNotFoundException r)
            {
                Console.WriteLine(r.ToString());
                provinces.Close();
                provinces.Dispose();
                cities.Close();
                cities.Dispose();
                return;
            }
        }

        private void closefile()//关闭文件
        {
            if (provinces != null)
            {
                provinces.Close();
                provinces.Dispose();
            }
            if (cities != null)
            {
                cities.Close();
                cities.Dispose();
            }
            if (areas != null)
            {
                areas.Close();
                areas.Dispose();
            }
        }
    }

       class math
    {
        public List<province> provinces { get; set; }
    }


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
