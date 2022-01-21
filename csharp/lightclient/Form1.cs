using System;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Windows.Forms;

namespace lightclient
{
    public partial class Form1 : Form
    {
        static Color start = Color.White;
        static Color end = Color.Red;
        SolidBrush brush = new SolidBrush(start);

        UdpClient client;

        int step, flag = 0;

        public Form1()
        {
            InitializeComponent();

            step = trackBar1.Maximum - trackBar1.Minimum;//长度进度条
        }

        private void pictureBox1_Paint(object sender, PaintEventArgs e)//初始化
        {
            Graphics g = e.Graphics;
            g.FillEllipse(brush, 50, 50, 200, 200);
        }

        private void button1_Click(object sender, EventArgs e)//开灯
        {
            linegb(1);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            linegb(3);
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            linegb(2);
        }

        private void linegb(int a)
        {
            if (a == 1)
            {
                brush = new SolidBrush(end);//开灯为红色
                pictureBox1.Refresh();//改变形状
                trackBar1.Value = trackBar1.Maximum;//进度条最小值
                button2.Enabled = true;
                button1.Enabled = false;
            }
            else if (a == 2)
            {
                int r = start.R + (end.R - start.R) * trackBar1.Value / step;
                int g = start.G + (end.G - start.G) * trackBar1.Value / step;
                int b = start.B + (end.B - start.B) * trackBar1.Value / step;

                brush = new SolidBrush(Color.FromArgb(r, g, b));//设置颜色，但未更新屏幕显示调用refresh显示
                pictureBox1.Refresh();

                //执行相应的禁用启用按钮
                if (trackBar1.Value == trackBar1.Minimum)
                {
                    button1.Enabled = true;
                    button2.Enabled = false;
                }
                else
                {
                    button1.Enabled = false;
                    button2.Enabled = true;
                }
                byte[] data = System.Text.Encoding.UTF8.GetBytes(trackBar1.Value.ToString());
                client.Send(data, data.Length);
            }
            else if (a == 3)
            {
                brush = new SolidBrush(start);//关灯为白色
                pictureBox1.Refresh();//改变形状
                trackBar1.Value = trackBar1.Minimum;//进度条最小值
                button1.Enabled = true;
                button2.Enabled = false;
            }
            flag = trackBar1.Value;
        }
        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            byte[] data = System.Text.Encoding.UTF8.GetBytes("off");
            client.Send(data, data.Length);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            client = new UdpClient();
            client.Connect(new IPEndPoint(IPAddress.Parse("127.0.0.1"), 9000));
            byte[] data = System.Text.Encoding.UTF8.GetBytes("on");
            client.Send(data, data.Length);
            try
            {
                client.BeginReceive(new AsyncCallback(recCallback), client);
            }
            catch
            {
                MessageBox.Show("服务器不在线！！！");
                this.Dispose();
            }
        }

        private void recCallback(IAsyncResult ar)
        {
            var client = (UdpClient)ar.AsyncState;
            var iep = (IPEndPoint)client.Client.LocalEndPoint;
            String data = System.Text.Encoding.UTF8.GetString(client.EndReceive(ar, ref iep));
            var a = int.Parse(data);
            if (a <= trackBar1.Maximum && a >= trackBar1.Minimum) flag = a;
            Console.WriteLine(flag);
            try
            {
                client.BeginReceive(new AsyncCallback(recCallback), client);
            }
            catch
            {
                MessageBox.Show("服务器不在线！！！");
            }
        }


        private void timer1_Tick(object sender, EventArgs e)
        {
            trackBar1.Value = flag;
            linegb(2);
        }

    }
}
