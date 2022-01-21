#include<iostream>
#include<cstring>
#include<cmath>
using namespace std;

template<class T>
T besides(T a,T b)
{
    if(b==0)
    {cout<<"除数分母不能为零!";exit(0);}
    return a/b;
}

template<class T>
T ride(T a,T b)
{
    return a*b;
}

template<class T>
T minuss(T a,T b)
{
    return a-b;
}

template<class T>
T add(T a,T b)
{
    return a+b;
}

int dzsint(char*a)
{
    int b=0;
    for(int i=0;a[i] != '\0';i++)
    {
    b = b*10 + int(a[i])-48;
    a[i] = '\0';
    }
    return b;
}


float dzsfloat(char*a)
{
    float b=0;
    int j=0,c=1;
    for(int i=0;a[i] != '\0';i++)
    {
        if(a[i]=='.')
        {
            j=1;
        }
        else if(j==0)
        {
            b = b*10 + float(a[i])-48;
        }
        else
        {
            b = b + (float(a[i])-48)/pow(10,c);
            c++;
        }
        a[i] = '\0'; 
    }
    return b;
}

union intandfloat
{
    int a;
    float b;
};

int pdsfyxsd(char *a)
{
    for(int i=0;a[i] != '\0';i++)
    {
        if(a[i]=='.') return 1;
    }
    return 0;
}

intandfloat pd(char *d,int& j)
{
    intandfloat a;
    if(pdsfyxsd(d)==0)
    {
    a.a=dzsint(d);
    j=0;
    }
    else
    {
    a.b=dzsfloat(d);
    j=1;
    }
    return a;
}

enum fh{addf,minusf,ridef,besidesf};

void js(fh a,int b,int c,intandfloat d,intandfloat e)
{
    if(a==addf)
    {
        if(b==0)
        {
            if(c==0)
            {
                cout<<"结果为:"<<add(d.a,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<add<float>(d.a,e.b)<<endl;
            }
            
        }
        else
        {
            if(c==0)
            {
                cout<<"结果为:"<<add<float>(d.b,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<add(d.b,e.b)<<endl;
            }
        }
        
    }
    
    if(a==minusf)
    {
        if(b==0)
        {
            if(c==0)
            {
                cout<<"结果为:"<<minuss(d.a,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<minuss<float>(d.a,e.b)<<endl;
            }
            
        }
        else
        {
            if(c==0)
            {
                cout<<"结果为:"<<minuss<float>(d.b,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<minuss(d.b,e.b)<<endl;
            }
        }
    }

    if(a==ridef)
    {
        if(b==0)
        {
            if(c==0)
            {
                cout<<"结果为:"<<ride(d.a,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<ride<float>(d.a,e.b)<<endl;
            }
            
        }
        else
        {
            if(c==0)
            {
                cout<<"结果为:"<<ride<float>(d.b,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<ride(d.b,e.b)<<endl;
            }
        }
    }    
    
    if(a==besidesf)
    {
        if(b==0)
        {
            if(c==0)
            {
                cout<<"结果为:"<<besides(d.a,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<besides<float>(d.a,e.b)<<endl;
            }
            
        }
        else
        {
            if(c==0)
            {
                cout<<"结果为:"<<besides<float>(d.b,e.a)<<endl;
            }
            else
            {
                cout<<"结果为:"<<besides(d.b,e.b)<<endl;
            }
        }
    }
}

int main()
{
    fh g=addf;
    char f,*d=new char[30];
    int i=0,j=0,k=0;
    intandfloat a,b;
    for(f=cin.get();41<int(f)&&int(f)<58&&int(f)!=44;f=cin.get())
    { 
        if(f == '.')
        {
            d[i]=f;
            i++;
        }
        else if(f == '+')
        {
            a=pd(d,j);
            g=addf;
            i=0;
        }
        else if(f == '-')
        {
            a=pd(d,j);
            g=minusf;
            i=0;
        }
        else if(f == '*')
        {
            a=pd(d,j);
            g=ridef;
            i=0;
        }
        else if(f == '/')
        {
            a=pd(d,j);
            g=besidesf;
            i=0;
        }
        else 
        {
            d[i]=f;
            i++;
        }
    }
    if(int(f)!=10&&(int(f)<42||int(f)>57||int(f)==44))
    {
        cout<<"运算表达式存在非法字符!"<<endl;
        return 0;
    }
    b=pd(d,k);
    js(g,j,k,a,b);
}