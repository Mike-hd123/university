#include<iostream>
using namespace std;

class circle
{
protected:
	float r;
public:
	circle(float r=0)
	{
		this->r=r;
	}
};

class cylinder:public circle
{
	float h;
public:
	cylinder(float r=0,float h=0)
	{
		this->r=r;
		this->h=h;
	}

	float getvolume()
	{
		return 3.1415*r*r*h;
	}


	float getarea()
	{
		return (2*3.1415*r*r)+(2*3.1415*r*h);
	}
};

int main()
{
	float r,h;
	cylinder *s1;
	cout<<"请输入半径和高:";
	cin>>r>>h;
	s1 = new cylinder(r,h);
	cout<<"面积:"<<s1->getarea()<<endl;
	cout<<"体积:"<<s1->getvolume()<<endl;
	delete s1;
}