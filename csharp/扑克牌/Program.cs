using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Xml;

namespace 扑克牌
{
    enum Points { A, two, three, four, five, six, seven, eight, nine, ten, J, Q, K };
    enum Colors { 黑桃, 红桃, 梅花, 方块 }
    class brands
    {
        public Points Point;
        public Colors Color;
        public brands(Points Point, Colors Colos)
        {
            this.Point = Point;
            this.Color = Colos;
        }

        public brands()
        {
            this.Point = 扑克牌.Points.A;
            this.Color = 扑克牌.Colors.方块;
        }
    }


    class brandsComparer : IComparer<brands>
    {
        // 区别于CompareTo()单参数，此处为双参数

        public int Compare([AllowNull] brands x, [AllowNull] brands y)
        {
            if (x.Point != y.Point)
            {
                return x.Point.CompareTo(y.Point);
            }
            else if (x.Color != y.Color)
            {
                return x.Color.CompareTo(y.Color);
            }
            else return 0;
        }

        class 扑克牌s
        {
            private List<brands>[] show = new List<brands>[4];
            brands[] brand = new brands[52];
            int max = 9999;//洗牌循环数

            public void create()
            {
                int i, j, z = 0;
                for (i = 0; i < 13; i++)//点数
                {
                    for (j = 0; j < 4; j++)//花色
                    {
                        brand[z++] = new brands((扑克牌.Points)i, (扑克牌.Colors)j);
                    }
                }
            }

            public void shuffle()
            {
                Random rd = new Random();
                brands exchange = new brands();
                int a, b;
                for (int i = 0; i < max; i++)
                {
                    do
                    {
                        a = rd.Next(0, 52);
                        b = rd.Next(0, 52);
                    } while (a == b);
                    exchange = brand[a];
                    brand[a] = brand[b];
                    brand[b] = exchange;
                }
            }

            public void Licensing()//发牌
            {
                int j;
                for (int i = 0; i < 4; i++)
                {
                    j = i;
                    show[i] = new List<brands>();
                    while (j < 52)
                    {
                        show[i].Add(brand[j]);
                        j += 4;
                    }
                    show[i].Sort(new brandsComparer());
                }
            }

            public void output()
            {
                int i = 0;
                foreach (brands a in brand)
                {
                    if (a.Point > 扑克牌.Points.A & a.Point < 扑克牌.Points.J) Console.Write("{0}.{1} ", (int)a.Point + 1, a.Color.ToString());
                    else Console.Write("{0}.{1} ", a.Point.ToString(), a.Color.ToString());
                    if (i++ % 13 == 12) Console.WriteLine();
                }
            }

            public void output1()
            {
                for (int i = 0; i < 4; i++)
                {
                    Console.Write("第{0}位的牌:",i+1);
                    foreach(brands a in show[i])
                    {
                        if (a.Point > 扑克牌.Points.A & a.Point < 扑克牌.Points.J) Console.Write("{0}.{1} ", (int)a.Point + 1, a.Color.ToString());
                        else Console.Write("{0}.{1} ", a.Point.ToString(), a.Color.ToString());
                    }
                    Console.WriteLine();
                }
            }
        }

        class other
        {
            static void Main(string[] args)
            {
                扑克牌s a = new 扑克牌s();
                a.create();
                Console.WriteLine("洗牌前:");
                a.output();
                a.shuffle();
                Console.WriteLine();
                Console.WriteLine("洗牌后:");
                a.output();
                Console.WriteLine();
                a.Licensing();//发牌
                a.output1();
            }
        }
    }
}