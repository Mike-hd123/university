using System;

namespace 进程调度
{
    enum stateen { R,E};

    class PCB
    {
        public String name;
        public PCB next;
        public int needtime;
        public int havetime;
        public stateen state;
        public PCB() { }
        public PCB(string name,PCB next,int need,int have,stateen state)
        {
            this.name = name;
            this.next = next;
            needtime = need;
            havetime = have;
            this.state = state;
        }

    }

    class circulate
    {
        public PCB head;
        public PCB tail;

        public circulate()//初始化
        {
            head = null;
            tail = null;
        }

        public void add(PCB a)//添加节点
        {
            if (head == null) //开始为空
            {
                head = tail = a;
                a.next = a;
            }
            else//不为空
            {
                a.next = head;
                tail.next = a;
                tail = a;
            }
        }

        public void remove(PCB a)//参数为要删除节点的前一个。
        {
            PCB c = a.next;
            c.state = stateen.E;
            if (a == a.next)//只剩自己
            {
                head = tail = null;
            }
            else//两个或两个以上
            {
                a.next = c.next;
                if (head == c)//被删除的是头节点
                {
                     head = a.next;
                }
                if(tail == c)//被删除的是尾节点
                {   
                    tail = a;
                }
            }
        }
    }

    class dispatch
    {
        PCB flag;
        circulate ready = new circulate(), finish = new circulate();
        public void creat()//创建5个进程
        {
            PCB a;
            Random rd = new Random();
            for (int i = 0; i < 5; i++)
            {
                a = new PCB("Q"+(i+1).ToString(),null, rd.Next(5, 10), 0,stateen.R);
                ready.add(a);
            }
            flag = ready.tail;
        }

        public void output(circulate a)//输出队列
        {
            PCB b = a.head;
            do
            {
                Console.WriteLine("名字:{0},需要时间:{1},已运行时间:{2},状态:{3}",b.name,b.needtime,b.havetime,b.state.ToString());
                b = b.next;
            } while (b != a.head);
        }

        public void clock()//时钟事件
        {
            PCB a = flag.next;
            a.havetime++;
            Console.WriteLine("名字:{0},需要时间:{1},已运行时间:{2},状态:{3}", a.name, a.needtime, a.havetime, a.state.ToString());
            if (a.needtime == a.havetime)
            {
                ready.remove(flag);
                finish.add(a);
                return;
            }
            flag = flag.next;
        }

        public void round()//中断信号
        {
            Console.WriteLine("调用前：");
            output(ready);
            Console.WriteLine("调用中：");
            while (ready.head!=null)
            {
                clock();
            }
            Console.WriteLine("调用后：");
            output(finish);
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            dispatch a = new dispatch();//调度类，对象
            a.creat();//创建进程
            a.round();//调度进程包括输出
        }
    }
}
