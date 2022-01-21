#include<iostream>
#include<cstring>
using namespace std;

void strcpys(char*&s1,char *s2)//没有设置函数，修改时直接把原来数据对象删除，故无需释放用来空间。
{
	s1 = new char[strlen(s2)+1];
	if(s1==NULL){cout<<"动态空间申请失败!!!";}
	strcpy(s1,s2);
}

float dzsfloat(char*a)
{
    float b=0;
    for(int i=0;a[i] != '\0';i++)
    b = b*10 + float(a[i])-48;
    return b;
}

class students
{
	char *id,*name,*bj;
	float language,math,english,total,aver;
public:
	students(char* id,char* name,char* bj,float languages=0,float maths=0,float englishs=0)
	{
		strcpys(this->name,name);
		strcpys(this->bj,bj);
		strcpys(this->id,id);
		language=languages;
		math=maths;
		english=englishs;
		total=language+math+english;
		aver=(language+math+english)/3;
	}

	~students()
	{
		delete id;
		delete name;
		delete bj;
	}

	void show()//输出信息
	{
		cout<<"学号:"<<id<<"\t姓名:"<<name<<"\t班级:"<<bj<<"\t语文:"<<language<<"\t数学:"<<math<<"\t英语:"<<english<<"\t总成绩:"<<total<<"\t平均成绩:"<<aver<<endl;
	}

	void showlanguage()//按科目查询成绩
	{
		cout<<"学号:"<<id<<"\t姓名:"<<name<<"\t班级:"<<bj<<"\t语文:"<<language<<endl;
	}

	void showmath()
	{
		cout<<"学号:"<<id<<"\t姓名:"<<name<<"\t班级:"<<bj<<"\t数学:"<<math<<endl;
	}

	void showenglish()
	{
		cout<<"学号:"<<id<<"\t姓名:"<<name<<"\t班级:"<<bj<<"\t英语:"<<english<<endl;
	}
	friend class link;
};

class node
{
	students *data;
	node *next;
	public:
	node(students *data=NULL,node *next=NULL)
	{
		this->data=data;
		this->next=next;
	}

	~node()
	{
		delete data;
	}
	
	friend class link;
};

struct  yxl
{
	char *bj;
	float bfl;
	yxl()
	{
		bj=NULL;
	}
	~yxl()
	{
		if(bj!=NULL) delete bj;
	}
};

class link
{
	node *head;
	public:
	link()
	{
		head=NULL;
	}

	~link()
	{
		node *p;
		for(p=head;head!=NULL;p=head)
		{
			head=head->next;
			delete p;
		}
	}

	void add()
	{
	    char id[500],name[500],bj[500];//只能输入250个中文。
	    float language,math,english,total,aver;
		node *p,*a,*b;
		int n;
		students *student;
		cout<<"请输入要管理的人数:"<<endl;
		cin>>n;
		for(int i=0;i<n;i++)
		{
			cout<<"正在输入第"<<i+1<<"个学生的\t学号\t姓名\t班级\t语文成绩\t数学成绩\t英语成绩:"<<endl;
			cin>>id>>name>>bj>>language>>math>>english;
			student = new students(id,name,bj,language,math,english);
			if(student==0){cout<<"动态空间申请失败!!!";}
			p=new node(student);
			if(head==NULL)
			{
				head=p;
				if(head==0){cout<<"动态空间申请失败!!!";}
			}
			else
			{
				for(a=b=head;b!=NULL;a=b,b=b->next)
				{
					if(strcmp(b->data->bj,p->data->bj)==0)
					{
						if(a!=head)
						{
						p->next=b;
						a->next=p;
						break;
						}
						else
						{
							p->next=head;
							head=p;
							break;
						}
					}
					if(b->next==NULL)
					{
						b->next=p;
						break;
					}
				}
			}
		}
	}

    void find()
    {
        const node *p=head;
        for(;p!=NULL;p=p->next)
		p->data->show();
    }

	void findid(char* id)
	{
		const node *p=head;
		for(;p!=NULL;p=p->next)
		{
			if(strcmp(p->data->id,id)==0) p->data->show();
		}
	}

	void findnb(int t,char *nb)
	{
		const node *p=head;
		for(;p!=NULL;p=p->next)
		{
			if(t==1 && strcmp(p->data->name,nb)==0)
			p->data->show();
			else if(t==2 && strcmp(p->data->bj,nb)==0)
			p->data->show();
		}
	}

	void finddk(int t)//按照单科成绩查询
	{
		const node *p=head;
		for(;p!=NULL;p=p->next)
		{
			if(t==1) p->data->showlanguage();
			else if(t==2) p->data->showmath();
			else if(t==3) p->data->showenglish();
		}
	}

	void deleid(char* id)//用id删除
	{
		node*p=head,*a=head;
		for(;p!=NULL;a=p,p=p->next)
		{
			if(strcmp(p->data->id,id)==0)
			{
                if(p==head)
                {
                    head=head->next;
                    delete p;
					cout<<"以删除!"<<endl;
					return ;
                }
                else
                {
                    a->next=p->next;
				    delete p;
					cout<<"以删除!"<<endl;
					return ;
                }
			}
		}
		cout<<"未找到!"<<endl;
	}

	students* input()
	{
		students *student;
		char name[500],bj[500],id[500];
	    float language,math,english,total,aver;
		cout<<"请修改学生的\t学号\t姓名\t班级\t语文成绩\t数学成绩\t英语成绩:"<<endl;
		cin>>id>>name>>bj>>language>>math>>english;
		student = new students(id,name,bj,language,math,english);
		if(student==0){cout<<"动态空间申请失败!!!";}
		return student;
	}

	void alterid(char* id)//修改用id
	{
		students *student;
		node*p=head,*a=head;
		for(;p!=NULL;a=p,p=p->next)
		{
			if(strcmp(p->data->id,id)==0)
			{
                if(p==head)
                {
					student=input();
                    delete p->data;
					p->data=student;
					cout<<"以修改!"<<endl;
					return ;
                }
                else
                {
					student=input();
				    delete p->data;
					p->data=student;
					cout<<"以修改!"<<endl;
					return ;
                }
			}
		}
		cout<<"未找到!"<<endl;
	}

	void shows(int t)//查寻最高分和最低分
	{
		const node *p=head,*a=head;
		for(;p!=NULL;p=p->next)
		{
		if(t==1 && p->data->language>a->data->language) a=p;
		else if(t==2 && p->data->math>a->data->math) a=p;
		else if(t==3 && p->data->english>a->data->english) a=p;
		else if(t==4 && p->data->language<a->data->language) a=p;
		else if(t==5 && p->data->math<a->data->math) a=p;
		else if(t==6 && p->data->english<a->data->english) a=p;
		}
		for(p=a;p!=NULL;p=p->next)
		{
			if((t==1 || t==4) && p->data->language==a->data->language)
			p->data->showlanguage();
			else if((t==2 || t==5) && p->data->math==a->data->math)
			p->data->showmath();
			else if((t==3 || t==6) && p->data->english==a->data->english)
			p->data->showenglish();
		}
	}

	void sortyb(yxl *a,int n)
	{
		int i=0,j=0,k=0;
		yxl c;
		for(;i<=n;i++)//选择排序
		{
			k=i;
			for(j=i+1;j<=n;j++)
			if(a[k].bfl<a[j].bfl)
			k=j;
			if(k!=i)
			{
				c.bfl=a[k].bfl;
				a[k].bfl=a[i].bfl;
				a[i].bfl=c.bfl;
				strcpys(c.bj,a[k].bj);
				strcpys(a[k].bj,a[i].bj);
				strcpys(a[i].bj,c.bj);
			}
		}
	}

	void getys(int t)//优秀
	{
		yxl g[30];
		node *p=head,*a=head;
		float j=0;
		int i=0,c=0,k=0,q=0;
		for(;p!=NULL;a=p,p=p->next)
		{
			if(strcmp(a->data->bj,p->data->bj)==0)
			{
				++i;//班级人数
				if(t==1 && p->data->language>=90) ++j;//优秀人数
				else if(t==2 && p->data->math>=90) ++j;//优秀人数
				else if(t==3 && p->data->english>=90) ++j;//优秀人数
			}
			else
			{
				strcpys(g[c].bj,a->data->bj);
				g[c].bfl = (j/i)*100;
				j=i=0;
				++c;
				++i;//班级人数
				if(t==1 && p->data->language>=90) ++j;//优秀人数
				else if(t==2 && p->data->math>=90) ++j;//优秀人数
				else if(t==3 && p->data->english>=90) ++j;//优秀人数
			}
		}
		strcpys(g[c].bj,a->data->bj);
		g[c].bfl = (j/i)*100;
		sortyb(g,c);
		for(i=0;i<=c;i++)
		{
			cout<<g[i].bj<<"班的优秀格率为:"<<g[i].bfl<<'%'<<endl;
		}
	}

	void getbs(int t)//不及格
	{
		yxl g[30];//仅支持30个班级排序。
		node *p=head,*a=head;
		float j=0;
		int i=0,c=0;
		for(;p!=NULL;a=p,p=p->next)
		{
			if(strcmp(a->data->bj,p->data->bj)==0)
			{
				++i;//班级人数
				if(t==1 && p->data->language<60) ++j;//不及格人数
				else if(t==2 && p->data->math<60) ++j;//不及格人数
				else if(t==3 && p->data->english<60) ++j;//不及格人数
			}
			else
			{
				strcpys(g[c].bj,a->data->bj);
				g[c].bfl = (j/i)*100;
				j=i=0;
				++c;
				++i;//班级人数
				if(t==1 && p->data->language<60) ++j;//不及格人数
				else if(t==2 && p->data->math<60) ++j;//不及格人数
				else if(t==3 && p->data->english<60) ++j;//不及格人数
			}
		}
		strcpys(g[c].bj,a->data->bj);
		g[c].bfl = (j/i)*100;
		sortyb(g,c);
		for(i=0;i<=c;i++)
		{
			cout<<g[i].bj<<"班的不及格率为:"<<g[i].bfl<<'%'<<endl;
		}
	}

	void sorts(int t)//排序
	{
		node*p=head,*a,*b;
		students *c;
		for(;p!=NULL;p=p->next)
		{
			b=p;
			for(a=p;a!=NULL;a=a->next)
			{
				if(t==1 && a->data->language>b->data->language && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
				else if(t==2 && a->data->math>b->data->math && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
				else if(t==3 && a->data->english>b->data->english && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
				else if(t==4 && a->data->total>b->data->total && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
				else if(t==5 && a->data->aver>b->data->aver && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
				else if(t==6 && dzsfloat(a->data->id)<dzsfloat(b->data->id) && strcmp(a->data->bj,b->data->bj)==0)
				b=a;
			}
			if(b!=p)
			{
				c=p->data;
				p->data=b->data;
				b->data=c;
			}
		}
	}
};

int main()
{
	link student;
    int i=0;
	char id[500],name[500],bj[500];//只能输入250个中文。
	float language,math,english,total,aver;
    while(i!=6)
    {
        cout<<"请选择功能\n1.添加\n2.查询\n3.排序\n4.修改\n5.删除\n6.退出"<<endl;
        cin>>i;
        if(i==1)//添加
        {
            student.add();
        }
        else if(i==2)//查询
        {
            cout<<"请选择查询方法\n1.全部\n2.姓名\n3.学号\n4.班级\n5.课程\n6.最高分\n7.最低分\n8.优秀率\n9.不及格率"<<endl;
            cin>>i;
			if(i==1)//全部
			{
				student.find();
			}
            else if(i==2)//姓名
            {
				cout<<"请输入姓名:";
				cin>>name;
				student.findnb(1,name);
			}
            else if(i==3)//学号
            {
				cout<<"请输入学号:";
				cin>>id;
				student.findid(id);
			}
            else if(i==4)//班级
            {
				cout<<"请输入班级:";
				cin>>bj;
				student.findnb(2,bj);
			}
            else if(i==5)//课程
            {
                cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.finddk(i);
            }
            else if(i==6)//最高分
            {
			    cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.shows(i);
			}
            else if(i==7)//最低分
            {
			    cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.shows(i+3);
            }
            else if(i==8)//优秀
            {
				cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.getys(i);
            }
            else if(i==9)//不及格
            {
				cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.getbs(i);
            }
            else
            {
                cout<<"未定义!"<<endl;
            }
        }
        else if(i==3)//排序
        {
            cout<<"请选择排序方法\n1.课程\n2.总成绩\n3.平均成绩\n4.学号"<<endl;
            cin>>i;
            if(i==1)
            {
                cout<<"请选择查询方法\n1.语文\n2.数学\n3.英语"<<endl;
                cin>>i;
				if(i>3||i<0) cout<<"未定义!"<<endl;
				else student.sorts(i);
            }
            else//其他
            {
				student.sorts(i+2);
			}
        }
        else if(i==4)//修改
        {
            cout<<"请输入要修改的学号:";
            cin>>id;
			student.alterid(id);
        }
        else if(i==5)//删除
        {
            cout<<"请输入要删除的学号:";
            cin>>id;
			student.deleid(id);
        }
		else if(i==6)
		{}
        else
        {
            cout<<"为定义!"<<endl;
        }
    }
}