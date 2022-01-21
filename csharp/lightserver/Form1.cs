using System;
using System.Collections.Generic;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Windows.Forms;

namespace lightserver
{
    public partial class Form1 : Form
    {
        static Color start = Color.White;
        static Color end = Color.Red;
        SolidBrush brush = new SolidBrush(start);

        UdpClient client;
        List<IPEndPoint> ClientList = new List<IPEndPoint>();

        int step, max = 100, min = 0, a1 = 0, a2 = 0, a3 = 0;
        string text1, text2, text3;

        public Form1()
        {
            InitializeComponent();
            step = max - min;
            trackBar1.Maximum = trackBar2.Maximum = trackBar3.Maximum = max;
            trackBar1.Minimum = trackBar2.Minimum = trackBar3.Minimum = min;
            text1 = text2 = text3 = "离线";
        }

        //所有的图像初始化
        private void pictureBox1_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            g.FillEllipse(brush, 20, 20, 90, 90);
        }

        private void pictureBox2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            g.FillEllipse(brush, 20, 20, 90, 90);
        }

        private void pictureBox3_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            g.FillEllipse(brush, 20, 20, 90, 90);
        }

        //控制函数
        private void linegb(int a, object Button1, object Button2, object prictureBox, object trackBar)
        {
            Button button1 = (Button)Button1;
            Button button2 = (Button)Button2;
            PictureBox pictureBox1 = (PictureBox)prictureBox;
            TrackBar trackBars = (TrackBar)trackBar;
            if (a == 1)
            {
                brush = new SolidBrush(end);//开灯为红色
                pictureBox1.Refresh();//改变形状
                trackBars.Value = max;//进度条最小值
                button2.Enabled = true;
                button1.Enabled = false;
            }
            else if (a == 2)
            {
                int r = start.R + (end.R - start.R) * trackBars.Value / step;
                int g = start.G + (end.G - start.G) * trackBars.Value / step;
                int b = start.B + (end.B - start.B) * trackBars.Value / step;

                brush = new SolidBrush(Color.FromArgb(r, g, b));//设置颜色，但未更新屏幕显示调用refresh显示
                pictureBox1.Refresh();

                //执行相应的禁用启用按钮
                if (trackBars.Value == trackBars.Minimum)
                {
                    button1.Enabled = true;
                    button2.Enabled = false;
                }
                else
                {
                    button1.Enabled = false;
                    button2.Enabled = true;
                }
            }
            else if (a == 3)
            {
                brush = new SolidBrush(start);//关灯为白色
                pictureBox1.Refresh();//改变形状
                trackBars.Value = min;//进度条最小值
                button1.Enabled = true;
                button2.Enabled = false;
            }
            a1 = trackBar1.Value;
            a2 = trackBar2.Value;
            a3 = trackBar3.Value;
            if (ClientList.Count > 0 & trackBars == trackBar1)
            {
                byte[] data = System.Text.Encoding.UTF8.GetBytes(trackBar1.Value.ToString());
                client.Send(data, data.Length, ClientList[0]);
            }
            else if (ClientList.Count > 1 & trackBars == trackBar2)
            {
                byte[] data = System.Text.Encoding.UTF8.GetBytes(trackBar2.Value.ToString());
                client.Send(data, data.Length, ClientList[1]);
            }
            else if (ClientList.Count > 2 & trackBars == trackBar3)
            {
                byte[] data = System.Text.Encoding.UTF8.GetBytes(trackBar3.Value.ToString());
                client.Send(data, data.Length, ClientList[2]);
            }

        }

        //事件区
        private void button1_Click(object sender, EventArgs e)
        {
            linegb(1, button1, button2, pictureBox1, trackBar1);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            linegb(3, button1, button2, pictureBox1, trackBar1);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            linegb(1, button4, button3, pictureBox2, trackBar2);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            linegb(3, button4, button3, pictureBox2, trackBar2);
        }

        private void button6_Click(object sender, EventArgs e)
        {
            linegb(1, button6, button5, pictureBox3, trackBar3);
        }

        private void button5_Click(object sender, EventArgs e)
        {
            linegb(3, button6, button5, pictureBox3, trackBar3);
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            linegb(2, button1, button2, pictureBox1, trackBar1);
        }

        private void trackBar2_Scroll(object sender, EventArgs e)
        {
            linegb(2, button4, button3, pictureBox2, trackBar2);
        }

        private void trackBar3_Scroll(object sender, EventArgs e)
        {
            linegb(2, button6, button5, pictureBox3, trackBar3);
        }

        private void Form1_Load(object sender, EventArgs e)//窗口创建
        {
            IPEndPoint iep = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 9000);
            client = new UdpClient(iep);
            client.BeginReceive(new AsyncCallback(recCallback), client);
        }

        //其他
        private void recCallback(IAsyncResult ar)
        {
            var client = (UdpClient)ar.AsyncState;
            var iep = (IPEndPoint)client.Client.LocalEndPoint;
            String data = System.Text.Encoding.UTF8.GetString(client.EndReceive(ar, ref iep));
            int index;


            if (data.Equals("on"))//发送连接消息
            {
                if (ClientList.Count < 3)//只有开机发送连接指令无需判断是否在服务器
                {
                    if (!ClientList.Contains(iep))
                    {
                        ClientList.Add(iep);
                    }
                }
                if (ClientList.Count == 1)
                {
                    text1 = "在线";
                    byte[] datas = System.Text.Encoding.UTF8.GetBytes(a1.ToString());
                    client.Send(datas, datas.Length, iep);
                }
                else if (ClientList.Count == 2)
                {
                    text2 = "在线";
                    byte[] datas = System.Text.Encoding.UTF8.GetBytes(a2.ToString());
                    client.Send(datas, datas.Length, iep);
                }
                else if (ClientList.Count == 3)
                {
                    text3 = "在线";
                    byte[] datas = System.Text.Encoding.UTF8.GetBytes(a3.ToString());
                    client.Send(datas, datas.Length, iep);
                }
            }
            else if (data.Equals("off"))//发生关闭消息
            {
                ClientList.Remove(iep);
                if (ClientList.Count == 0)
                {
                    text1 = "离线";
                }
                else if (ClientList.Count == 1)
                {
                    text2 = "离线";
                }
                else if (ClientList.Count == 2)
                {
                    text3 = "离线";
                }
            }
            else//其他发送数值
            {
                index = ClientList.IndexOf(iep);
                int a = int.Parse(data);
                if (index == 0) a1 = a;
                if (index == 1) a2 = a;
                if (index == 2) a3 = a;
            }
            client.BeginReceive(new AsyncCallback(recCallback), client);
        }//消息到达

        private void timer1_Tick(object sender, EventArgs e)//用于线程同步，即将分控端的操作同步到服务端
        {
            if (trackBar1.Value != a1 && a1 <= max && a1 >= min)
            {
                trackBar1.Value = a1;
                trackBar1_Scroll(trackBar1, new EventArgs());
            }
            if (trackBar2.Value != a2 && a2 <= max && a2 >= min)
            {
                trackBar2.Value = a2;
                trackBar2_Scroll(trackBar2, new EventArgs());
            }
            if (trackBar3.Value != a3 && a3 <= max && a3 >= min)
            {
                trackBar3.Value = a3;
                trackBar3_Scroll(trackBar3, new EventArgs());
            }
            label1.Text = text1;
            label2.Text = text2;
            label3.Text = text3;
        }

    }
}
