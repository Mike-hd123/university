using System;
using System.Collections.Generic;
using System.Linq;

namespace 内存调度
{
    class Program
    {
        private int x { get; set; }
        private float conut,conuts;
        public float zdl;
        private List<int> Ready,go;

        public Program()
        {
            conut = x = 0;
            Ready = new List<int>();
            go = new List<int>();
        }

        public void MyRun()//先来先服务
        {
            conuts=Ready.Count;
            while (Ready.Count > 0)
            {
                if (!go.Contains(Ready[0]))
                {
                    if(go.Count==x) go.Remove(go[0]);
                    go.Add(Ready[0]);
                    conut++;//中断次数
                }
                Ready.Remove(Ready[0]);
            }
            zdl = conut / conuts;
        }

        static void Main(string[] args)
        {
            Program w = new Program();
            int a;
            Console.WriteLine("plese input adddress list:");
            string str = Console.ReadLine();//从控制台读入输入
            string[] nums = str.Split(new string[] { " " }, StringSplitOptions.None);//这里是以空格隔开
            foreach(string s in nums)
            {
                a = Convert.ToInt32(s);
                w.Ready.Add(a);
            }
            Console.WriteLine("plese input page size:");
            w.x  = Convert.ToInt32(Console.ReadLine());
            w.MyRun();
            Console.WriteLine("中断率为:{0}",w.zdl);
        }
    }
}
