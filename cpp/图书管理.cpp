#include<iostream>
#include<cstring>
using namespace std;

char* strcpys(char *s)//仅用来初始化，没有修改功能故无需释放原空间。
{
	char *p;
	p = new char[strlen(s+1)];
	if(p==0){cout<<"申请动态空间失败!"<<endl;}
	strcpy(p,s);
	return p;
}

class book
{
	char *name,*author,*press,*time;
    float price;
public:
	book(char*name,char*author,char*press,char*time,float price=0)
	{
        this->name=strcpys(name);
        this->author=strcpys(author);
        this->press=strcpys(press);
        this->time=strcpys(time);
        this->price=price;
	}

	~book()
	{
		delete name;
		delete author;
		delete press;
		delete time;
	}

    void show()
    {
        cout<<"书名:"<<name<<"\n作者:"<<author<<"\n出版社:"<<press<<"\n出版时间:"<<time<<"\n价格:"<<price<<endl<<endl;
    }
	friend class link;	
};

class node
{
	book *data;
	node *next;
	public:
	node(book *data=NULL,node *next=NULL)
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

	void add()//添加或创建链表
	{
		char name[500],author[500],press[500],time[50];
        float price;
		int n;
		cout<<"请输入要添加的书本数:"<<endl;
		cin>>n;
		for(int i=0;i<n;i++)
		{
			cout<<"正在输入第"<<i+1<<"本书的书名\t作者名\t出版社\t出版时间\t价格:"<<endl;
			cin>>name>>author>>press>>time>>price;
			book *a = new book(name,author,press,time,price);
			if(a==0){cout<<"申请动态空间失败!"<<endl;}
			if(head==NULL)
			{
				head = new node(a);
			}
			else
			{
				head = new node(a,head);
			}
		}
	}

	void show()//输出全部
	{
		node*p=head;
		int i=0;
		for(;p!=NULL;p=p->next)
		{
			++i;
			p->data->show();
		}
		cout<<"共"<<i<<"个"<<endl<<endl;
	}

	void refname(char*name)//课本名查询
	{
		node*p=head;
		int i=0;
		for(;p!=NULL;p=p->next)
		{
			if(strcmp(p->data->name,name)==0)
			{
				p->data->show();
				++i;
			}
		}
		cout<<"查找到"<<i<<"个"<<endl<<endl;
	}

	void refauthor(char*author)//作者名查询
	{
		node*p=head;
		int i=0;
		for(;p!=NULL;p=p->next)
		{
			if(strcmp(p->data->author,author)==0)
			{
				p->data->show();
				++i;
			}
		}
		cout<<"查找到"<<i<<"个"<<endl<<endl;
	}

	void delename(char*name)
	{
		node*p=head,*a=head;
		int i=0;
		for(;p!=NULL;a=p,p=p->next)
		{
			if(strcmp(p->data->name,name)==0)
			{
                if(p==head)
                {
                    head=head->next;
                    delete p;
                }
                else
                {
                    a->next=p->next;
				    delete p;
                }
                p=a;
				++i;
			}
		}
		cout<<"已删除"<<i<<"个"<<endl;
	}
};

int main()
{
	link books;
	int i=0;
	char user[50];
	cout<<"user:";
	cin>>user;
	while(strcmp(user,"admin")!=0)
	{
		cout<<"账号不存在!"<<endl;
		cout<<"请重新输入:";
		cin>>user;
	}
  while(i!=6)
  {
	char na[500];
	cout<<"请选择功能:\n1.添加\n2.输出\n3.查询(书名)\n4.查询(作者)\n5.删除\n6.退出"<<endl;
	cin>>i;
	if(i==1)
	{
		books.add();
	}
	else if(i==2)
	{
		books.show();
	}
	else if(i==3)
	{
		cout<<"请输入书名:";
		cin>>na;
		books.refname(na);
	}
	else if(i==4)
	{
		cout<<"请输入作者:";
		cin>>na;
		books.refauthor(na);
	}
	else if(i==5)
	{

		cout<<"请输入书名:";
		cin>>na;
		books.delename(na);
	}
	else if(i==6)
	{
		return 0;
	}
	else
	{
		cout<<"未定义!"<<endl;
	}
	}
}