#include<iostream>
using namespace std;

float fun(int n)
{
	float j=1,s=1;
	for(int i=2;i<=n;i++)
	s+=1.0/(j+=i);
	return s;
}

int main()
{
	int n;
	cout<<"请输入S=1+1/(1+2)+1/(1+2+3)+…….+1/(1+2+3+4+……+n)中的n值:"<<endl;
	cin>>n;
	cout<<"fun("<<n<<"):"<<fun(n)<<endl;
}