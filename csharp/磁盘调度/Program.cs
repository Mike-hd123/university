using System;
using System.Collections.Generic;
using System.Linq;

namespace 磁盘调度
{
    class Program
    {
        List<int> sxlist = new List<int>();
        int start { get; set; }
        int state { get; set; }

        public Program()
        {
            Random sjs = new Random();
            for (int i = 0; i < sjs.Next(11, 20); i++)//这里无错误，就是每次随机取，范围是11~21,只是数字越大概率越小。
            {
                sxlist.Add(sjs.Next(0, 199));//将生成的随机数放到列表中
            }
            Console.Write("生成列表:");
            foreach (int a in sxlist)
            {
                Console.Write(" {0}", a);
            }
            Console.WriteLine(" 共{0}个",sxlist.Count);
        }

        public void fcfs()
        {
            int sun = 0,b=start;
            Console.Write("移动顺序:");
            foreach(int a in sxlist)
            {
                Console.Write(" {0}",a);
                sun += Math.Abs(a - b);
                b = a;
            }
            Console.WriteLine();
            Console.Write("移动总柱面数:{0}",sun);
            Console.WriteLine();
        }

        public void sstf()
        {
            List<int> z = new List<int>(sxlist);
            int sun = 0, min,s=0, b = start,j;//存放移臂总数、当前每一次循环的最小距离、访问存储器、磁头当前位置、最小距离缓冲器
            Console.Write("移动顺序:");
            while (0 < z.Count)
            {
                min = 200;
                foreach (int a in z)
                {
                    j = Math.Abs(a - b);
                    if (min > j)
                    {
                        min = j;//更新最小差值
                        s = a;//更新访问顺序
                    }
                }
                b = s;
                sun += min;
                Console.Write(" {0}", s);
                z.Remove(s);
            }
            Console.WriteLine();
            Console.Write("移动总柱面数:{0}", sun);
            Console.WriteLine();
        }

        public void LOOK()
        {
            int sun;
            List<int> a=new List<int>(), b = new List<int>();
            foreach(int x in sxlist)
            {
                if (x >= start)  a.Add(x);
                else b.Add(x);
            }
            a.Sort();
            b.Sort();
            b.Reverse();
            Console.Write("移动顺序:");
            if (state == 0)//正向
            {
                sun = (sxlist.Max() - start) * 2 + (start - sxlist.Min());
                foreach (int x in a)
                {
                    Console.Write(" {0}", x);
                }
                foreach (int x in b)
                {
                    Console.Write(" {0}", x);
                } 
            }
            else//逆向
            {
                sun = (sxlist.Max() - start)  + (start - sxlist.Min()) * 2;
                foreach (int x in b)
                {
                    Console.Write(" {0}", x);
                }
                foreach (int x in a)
                {
                    Console.Write(" {0}", x);
                }

            }
            Console.WriteLine();
            Console.Write("移动总柱面数:{0}", sun);
            Console.WriteLine();
        }

        static void Main(string[] args)
        {
            int sx=4;
            Program a = new Program();
            while (sx < 5 && sx > 0)
            {
                Console.Write("请输入选择 1、fcfs 2、sstf 3、look 4、重新创建访问列表 其他、exit:");
                sx = Convert.ToInt32(Console.ReadLine()); 
                if (sx == 1)
                {
                    Console.Write("请输入磁头当前位置:");
                    a.start = Convert.ToInt32(Console.ReadLine());
                    a.fcfs();
                }
                else if (sx == 2)
                {
                    Console.Write("请输入磁头当前位置:");
                    a.start = Convert.ToInt32(Console.ReadLine());
                    a.sstf();
                }
                else if (sx == 3)
                {
                    Console.Write("请输入磁头当前位置:");
                    a.start = Convert.ToInt32(Console.ReadLine());
                    Console.Write("请输入磁头移动方向(0 正,other 逆向):");
                    a.state = Convert.ToInt32(Console.ReadLine());
                    a.LOOK();
                }
                else if (sx == 4) a = new Program();
            }
        }
    }
}
