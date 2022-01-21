using System;
using System.IO;
using System.Windows.Forms;

namespace treeview
{
    public partial class Form1 : Form
    {
        StreamReader provinces, areas, cities;//三个文件对象
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)//打开程序自动加载
        {
            openfile();
            AddTreeNodeRead();
            closefile();
        }
        private void AddTreeNodeRead()
        {
            TreeNode a = null, b = null;//三级节点的父节点
            string provincesline, citiesline, areasline;//三个文件的当前行
            string[] provincestext, citiestext = null, areastext = null;//三行解析出来的文本 1为地点 0总是等于2
            provincesline = provinces.ReadLine();//先读一行
            citiesline = cities.ReadLine();
            areasline = areas.ReadLine();
            if (citiesline != null) citiestext = citiesline.Split(',');//读后处理
            if (areasline != null) areastext = areasline.Split(',');
            do//省节点
            {
                provincestext = provincesline.Split(',');
                a = treeView1.Nodes.Add(provincestext[1].Trim('"'));
                do//市节点
                {
                    b = a.Nodes.Add(citiestext[1].Trim('"'));
                    do//区
                    {
                        b.Nodes.Add(areastext[1].Trim('"'));
                        areasline = areas.ReadLine();
                        if (areasline != null) areastext = areasline.Split(',');
                    } while (areasline != null & citiestext[0].Equals(areastext[2]));
                    citiesline = cities.ReadLine();
                    if (citiesline != null) citiestext = citiesline.Split(',');
                } while (citiesline != null & provincestext[0].Equals(citiestext[2]));
            } while ((provincesline = provinces.ReadLine()) != null);
        }

        private void openfile()//打开文件
        {
            try
            {
                provinces = new StreamReader(System.Environment.CurrentDirectory + "\\provinces.csv");
                provinces.ReadLine();//去除头行的code等表示
            }
            catch (FileNotFoundException r)
            {
                MessageBox.Show(r.ToString());
                return;
            }
            try
            {
                cities = new StreamReader(System.Environment.CurrentDirectory + "\\cities.csv");
                cities.ReadLine();
            }
            catch (FileNotFoundException r)
            {
                MessageBox.Show(r.ToString());
                provinces.Close();//申请失败清理前面成功打开的文件
                provinces.Dispose();
                return;
            }
            try
            {
                areas = new StreamReader(System.Environment.CurrentDirectory + "\\areas.csv");
                areas.ReadLine();
            }
            catch (FileNotFoundException r)
            {
                MessageBox.Show(r.ToString());
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
}
