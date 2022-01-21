#include<iostream>
using namespace std;

class point
{
	float x,y;
public:
	point(float x=0,float y=0):x(x),y(y){}

	friend class rectangle;
};

class rectangle
{
	point s1,s2;
public:
	rectangle(float x1=0,float y1=0,float x2=0,float y2=0):s1(x1,y1),s2(x2,y2){}//左下和右上

	void getcoordin()
	{
		cout<<"左上角坐标:"<<'('<<s1.x<<','<<s2.y<<')'<<endl;
		cout<<"右上角坐标:"<<'('<<s2.x<<','<<s2.y<<')'<<endl;
		cout<<"左下角坐标:"<<'('<<s1.x<<','<<s1.y<<')'<<endl;
		cout<<"右下角坐标:"<<'('<<s2.x<<','<<s1.y<<')'<<endl;
	}

	void getarea()
	{
		cout<<"面积:"<<(s2.x-s1.x)*(s2.y-s1.y)<<endl;
	}
};

int main()
{
	float x1,y1,x2,y2;
	rectangle *s;
	cout<<"请输入左下角的坐标:"<<endl;
	cin>>x1>>y1;
	cout<<"请输入右上角的坐标:"<<endl;
	cin>>x2>>y2;
	s = new rectangle(x1,y1,x2,y2);
	s->getcoordin();
	s->getarea();
	delete s;
}