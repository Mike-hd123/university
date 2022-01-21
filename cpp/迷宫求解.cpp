#include<iostream>
#include<fstream>
#include<unistd.h>
#include<string>
#include<set>
using namespace std;

class node1{//存放路径
    public:
    int x,y;
    node1* next;
    node1(node1*a,int x,int y)
    {
        this->x=x;
        this->y=y;
        next=a;
    }
};

class node{//保存所有路径
    public:
    int sun;
    node1* next1;
    node* next;
    node(node*a,int sun){
        this->sun=sun;
        next=a;
    }
};

class date{//存放当前位置的下标
    public:
    int x,y;
};

class maze{//主体

private:
int **a=NULL,//图数组
    i,//数组的行
    j,//列
    startx=0,//起点
    starty=0,
    endx=0,//终点
    endy=0,
    d=-1,//距离起点距离
    mode;//模式标志
date* g;//运行时存放路径
node* head=NULL;//结束后存放路径
set<int> sizex;//存放可以选择的路径长度

public:
string getDirectory();//获取执行目录
void gui();//开始运行调用
void create();//创建迷宫
void read();//从文件读入迷宫数据
void start();//开启超强模式探测
void startNormal();//普通模式
void detection(int i,int j);//超强模式探测
void detectionNormal(int i,int j,int z);//普通模式探测
void save();//保存路径
void shortl();//路径排序
void write();//把运行结果输出到文件
void choose();//显示可以选择的个数
bool judge(int x,int y);//判断输入的起终点是否合法，返回真为输入不合法
bool judges(int z,int x,int y=0);//判断输入的路径大小是否满足要求 1、2、3、4分别为小于x大于y、小于x、大于x、等于x
void output(int z,int x=-1,int y=-1);//输出路径0、全部1、最小2、指定3、小于4、大于5、小于x大于y
void showold();//输出原来的地图
void shownew();//输出标记后的地图
void hft();//恢复图为重新输入起终点做准备
void deletu();//销毁图
void delelb();//销毁链表
void dele();//销毁所有内存
void delesz();//销毁标记数组
};

string maze::getDirectory(){
    char abs_path[1024];
    int cnt = readlink("/proc/self/exe", abs_path, 1024);//获取可执行程序的绝对路径
    if(cnt < 0|| cnt >= 1024)
    {
        return NULL;
    }
    for(int i = cnt; i >= 0; --i)
    {
        if(abs_path[i]=='/')//找最后一个/因为后面为可执行文件名
        {
            abs_path[i + 1]='\0';
            break;
        }
    }
    return abs_path;
}

void maze::create(){
    deletu();
    int r,s,x;
    cout<<"请输入迷宫的长宽:";
    cin>>i>>j;
    a = new int*[i];//图的创建
    for(r=0;r<i;r++)
    {
        cout<<"第"<<r+1<<"行:";
        a[r] = new int[j];
        for(s=0;s<j;s++)
        {
            cin>>x;
            a[r][s] = x==0?x:1;
        }
    }
    g=new date[(i-1)*(j-1)];//标记数组申请
    cout<<"请输入起点:";
    cin>>startx>>starty;
    while (judge(startx,starty))
    {
        cout<<"请重新输入起点(输入0 0退出):";
        cin>>startx>>starty;
        if(startx==0 && starty==0){
            dele();
            return;
        }
    }
    cout<<"请输入终点:";
    cin>>endx>>endy;
    while (judge(endx,endy))
    {
        cout<<"请重新输入终点(输入0 0退出):";
        cin>>endx>>endy;
        if(endx==0 && endy==0){
            dele();
            return;
        }
    }
    cout<<"请选择模式(0为普通模式，非0为超强模式):";
    cin>>mode;
    if(mode) start();//超强模式
    else startNormal();//普通模式
}

void maze::read(){
    int r,s,x;
    ifstream infile;   //输入流
 
    infile.open(getDirectory()+"date.txt", ios::in); 
    if(!infile.is_open ()){
        cout << "文件打开失败!" << endl;
        cout<<"请检查文件运行目录是否存在:date.txt"<<endl;
    } 
    infile>>mode>>i>>j>>startx>>starty>>endx>>endy;
    a = new int*[i];//图的创建
    for(r=0;r<i;r++)
    {
        a[r] = new int[j];
        for(s=0;s<j;s++)
        {
            if(infile.eof()){
                cout<<"文件已经结尾无法完成读取，请检查文件内容是否正确。"<<endl;
                deletu();
                return;
            }
            infile>>x;
            a[r][s] = x==0?0:1;
        }
    }          
    infile.close();   //关闭文件
    g=new date[(i-1)*(j-1)];//标记数组申请
    cout<<"读取结果为:"<<endl;
    cout<<"长宽:"<<i<<' '<<j<<endl;
    cout<<"图:"<<endl;
    for(r=0;r<i;r++){
        for(s=0;s<j;s++){
            cout<<a[r][s]<<' ';
        }
        cout<<endl;
    }
    cout<<"模式为:";
    if(mode) cout<<"超强模式"<<endl;
    else cout<<"普通模式"<<endl;
    cout<<"起点为:"<<startx<<' '<<starty<<endl;
    cout<<"终点为:"<<endx<<' '<<endy<<endl;
    while (judge(startx,starty))
    {
        cout<<"请手动输入起点(输入0 0退出):";
        cin>>startx>>starty;
        if(startx==0 && starty==0){
            dele();
            return;
        }
    }
    while (judge(endx,endy))
    {
        cout<<"请手动输入终点(输入0 0退出):";
        cin>>endx>>endy;
        if(endx==0 && endy==0){
            dele();
            return;
        }
    }
    if(mode) start();//超强模式
    else startNormal();//普通模式
}

void maze::start(){
    delelb();//销毁原来的遍历记录
    hft();//恢复原来的图
    d=-1;
    detection(startx,starty);
    if(head==NULL){
        cout<<"不存在可行路径！！！请更换图或起终点后再试"<<endl;
    }
    shortl();//对路径排序
}

void maze::startNormal(){	
    delelb();
    hft();//恢复原来的图	    
    d=-1;	   
    if(startx<=endx and starty<=endy)//探测右下	
    detectionNormal(startx,starty,1);   
    if(startx<=endx and starty<=endy)//探测右上	
    detectionNormal(startx,starty,2);	
    if(startx>=endx and starty<=endy)//探测左上	
    detectionNormal(startx,starty,3);	
    if(startx>=endx and starty>=endy)//探测右上	
    detectionNormal(startx,starty,4);	
    if(head==NULL){
        cout<<"不存在可行路径！！！请更换图或起终点后再试"<<endl
            <<"1、起点与终点的对角线两侧不能有墙的存在。"<<endl;	
    }
    shortl();//对路径排序
}

void maze::detection(int i,int j){
    if(a[i][j]==1) return;//墙
    for(int b=0;b<d;b++)//以走防止重复
    {
        if(g[b].x==i && g[b].y==j)
        {
            return ;
        }
    }
    d++;
    g[d].x=i;
    g[d].y=j;
    if(i==endx && j==endy) save();
    detection(i+1,j);
    detection(i-1,j);
    detection(i,j+1);
    detection(i,j-1);
    d--;  
}

void maze::detectionNormal(int i,int j,int z){
    if(a[i][j]==1) return;
    d++;
    g[d].x=i;g[d].y=j;
    if(i==endx && j==endy) save();
    if(z==1){//探测右下
        detectionNormal(i+1,j,z);
        detectionNormal(i,j+1,z);
    }
    else if(z==2){//探测右上
        detectionNormal(i+1,j,z);
        detectionNormal(i,j-1,z);
    }
    else if(z==3){//探测左上
        detectionNormal(i-1,j,z);
        detectionNormal(i,j-1,z);
    }
    else if(z==4){//探测左下
        detectionNormal(i-1,j,z);
        detectionNormal(i,j+1,z);
    }
    d--;
}

void maze::save(){
    node *p=new node(head,d);
    head=p;
    node1 *s=NULL;
    for(int i=d;-1<i;i--)
    {
        s = new node1(s,g[i].x,g[i].y);
        a[g[i].x][g[i].y]=-1;//标记通路为-1
    }
    p->next1=s;
    sizex.insert(d);
}

void maze::shortl(){
    node *a,*b,*c;
    int sun;
    node1 *d;
    a=head;
    while(a!=NULL){
        b=c=a;
        while (b!=NULL)
        {
            if(b->sun<c->sun) c=b;
            b=b->next;
        }
        if(a!=c){
            sun=c->sun;
            d=c->next1;
            c->sun=a->sun;
            c->next1=a->next1;
            a->sun=sun;
            a->next1=d;
        }
        a=a->next;
    }
}

void maze::write(){
    node *p=head;
    node1 *s;
    int i,j;
    ofstream outfile;   //输出流
    outfile.open(getDirectory()+"print.txt",ios_base::out);
    outfile<<"模式为:";
    if(mode) outfile<<"超强模式"<<endl;
    else outfile<<"普通模式"<<endl;
    outfile<<"原图为:"<<endl;
    for(int i=0;i<this->i;i++){
        for(int j=0;j<this->j;j++){
            if(a[i][j]<1)
                outfile<<0<<' ';
            else
                outfile<<1<<' ';
        }
        outfile<<endl;
    }
    outfile<<"标记图为:"<<endl;
    for(i=0;i<this->i;i++){
        for(j=0;j<this->j;j++){
            if(a[i][j]!=-1) outfile<<' ';//平衡输出对齐
            outfile<<a[i][j]<<' ';  
        }
        outfile<<endl;
    }
    outfile<<"路径为:"<<endl;
    i=0;
    while (p!=NULL)
    {
        s=p->next1;
        outfile<<"路径"<<i+1<<':';
        while (s!=NULL)
        {
            outfile<<'('<<s->x<<','<<s->y<<')'<<' ';
            s=s->next;
        }
        outfile<<"长为:"<<p->sun<<endl;
        p=p->next;
        i++;
    }
    outfile<<"共"<<i<<"条路径"<<endl;
    outfile.close();
}

void maze::choose(){
    set<int>::iterator it; //定义前向迭代器
    cout<<"路径可选大小:";
    for(it = sizex.begin(); it != sizex.end(); it++)  
    {  
        cout << *it << " ";  
    }  
    cout << endl;
    cout<<"请输入大小或范围:";
}

bool maze::judge(int x,int y){
    if(0<x and x<i and 0<y and y<j and a[x][y]!=1) return false;
    else{
        cout<<endl<<"你的输入不合法!!!"<<endl
        <<"1、a[x][y]不能为:1"<<endl
        <<"2、x或y不能小于0也不能分别大于i,j"<<endl<<endl;
        return true;
    }
}

bool maze::judges(int z,int x,int y){//1、2、3、4分别为小于x大于y、小于x、大于x、等于x
    set<int>::iterator it; //定义前向迭代器
    for(it = sizex.begin(); it != sizex.end(); it++)  
    {
        if(z==1 && x<*it && *it<y) return false;//小于x大于y
        if(z==2 && *it<x) return false;//小于x
        if(z==3 && *it>x) return false;//大于x
        if(z==4 && *it==x) return false;//等于x  
    }
    cout<<"输入的范围不存在路径！！！,请重新输入(输入0退出):";
    return true;//返回真即没有符合条件的路径
}

void maze::output(int z,int x,int y){//输出路径
    node *p=head;
    node1 *s;
    int i=0,q;
    if(p) q=p->sun;
    while (p!=NULL)
    {
        s=p->next1;
        if(z==0||p->sun==q && z==1||p->sun==x && z==2||p->sun<x && z==3||p->sun>x && z==4||x<p->sun && p->sun<y && z==5){
        cout<<"路径:";
        while (s!=NULL)
        {
            cout<<'('<<s->x<<','<<s->y<<')'<<' ';
            s=s->next;
        }
        cout<<"长为:"<<p->sun<<endl;
        i++;
        }
        if(z==1 && p->sun!=q||z==2 && p->sun>x ||z==3 && p->sun>x||z==5&&p->sun>y) break;
        p=p->next;
    }
    cout<<"共"<<i<<"条路径"<<endl;
}

void maze::showold(){
    cout<<"原图为:"<<endl;
    for(int i=0;i<this->i;i++){
        for(int j=0;j<this->j;j++){
            if(a[i][j]<1)
                cout<<0<<' ';
            else
                cout<<1<<' ';
        }
        cout<<endl;
    }
}

void maze::shownew(){
    cout<<"标志图为:"<<endl;
    for(int i=0;i<this->i;i++){
        for(int j=0;j<this->j;j++){
            if(a[i][j]==-1)
                cout<<'-'<<' ';
            else
                cout<<1<<' ';  
        }
        cout<<endl;
    }
}

void maze::hft(){
    if(a==NULL){ cout<<"请先创建图！"<<endl;return;}
    for(int i=0;i<this->i;i++){
        for(int j=0;j<this->j;j++){
            if(a[i][j]<1)
                a[i][j]=0;
            else
                a[i][j]=1;
        }
    }
}

void maze::dele(){
    deletu();//销毁图
    delesz();//销毁标记数组
    delelb();//销毁链表
}

void maze::deletu(){
    if(a==NULL) return;
    for(int i=0;i<this->i;i++)
        delete a[i];
    delete a;
    a=NULL;
}

void maze::delesz(){
    if(g==NULL) return;
    delete g;
    g=NULL;
}

void maze::delelb(){
    if(head==NULL) return;
    sizex.clear();//清空元素
    node *p=head,*t;
    node1 *s,*b;
    while (p!=NULL)
    {
        s=p->next1;
        while (s!=NULL)
        {
            b=s;
            s=s->next;
            delete b;
        }
        t=p;
        p=p->next;
        delete t;
    }
    head=NULL;
}

void maze::gui(){
    int xz = 0,x=0,y=0;
    bool f;
    cout << "*************************************" << endl
         << "*              欢迎使用              *" << endl
         << "*             迷宫求解器             *" << endl
         << "*************************************" << endl;
    while(true)
    {
        f=false;//防止上次执行时覆盖为true，用于停止跳出循环
        cout<<"*************************************"<<endl
            <<"*          1、创建迷宫               *"<<endl
            <<"*          2、导入迷宫               *"<<endl
            <<"*          3、重置起点               *"<<endl
            <<"*          4、重置终点               *"<<endl
            <<"*          5、切换模式               *"<<endl
            <<"*          6、打印结果               *"<<endl
            <<"*          7、输出路径               *"<<endl
            <<"*          8、输出原图               *"<<endl
            <<"*          9、输出标记图             *"<<endl
            <<"*          10、输出最优路径           *"<<endl
            <<"*          11、输出指定路径           *"<<endl
            <<"*          12、输出小于x路径          *"<<endl
            <<"*          13、输出大于x路径         *"<<endl
            <<"*          14、输出大于x小于y路径    *"<<endl
            <<"*          15、退出程序              *"<<endl
            <<"*************************************"<<endl;
        cout<<"请输入你的选项:";
        cin>>xz;
        if (xz == 1)//创建迷宫
            create();
        else if(xz == 2){//导入迷宫
            read();
        }
        else if (xz == 3){//重置起点
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        do
        {
            cout<<"请输入起点(输入0 0退出):";
            cin>>x>>y;
            if(x==0 and y==0){f=true;break;}
        } while (judge(x,y));
        if(f) continue;
        startx=x;
        starty=y;
        start();
        }
        else if (xz == 4){//重置终点
        if(a==NULL) { cout<<"请先创建图!!!"<<endl;continue;}
        do
        {
            cout<<"请输入终点(输入0 0退出):";
            cin>>x>>y;
            if(x==0 and y==0){f=true;break;};
        } while (judge(x,y));
        if(f) continue;
        endx=x;
        endy=y;
        start();
        }
        else if(xz == 5){//切换模式
            if(a==NULL){cout<<"请先创建图!!!"<<endl;continue;}
            cout<<"请选择模式(0为普通模式，非0为超强模式):";
            cin>>x;
            if(mode==x) continue;//如果模式没用发生改变
            else mode=x;
            if(mode) start();//超强模式
            else startNormal();//普通模式
        }
        else if(xz == 6){//打印结果(输出到print文件)
            if(a==NULL){cout<<"请先创建图!!!"<<endl;continue;}
            if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
            write();
        }
        else if (xz == 7){//输出路径
        if(a==NULL){cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        output(0);
        }
        else if (xz == 8){//输出原图
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        showold();
        }
        else if(xz == 9){//输出标记图
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        shownew();
        }
        else if (xz == 10){//输出最优路径
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        output(1);
        }
        else if (xz == 11){//输出指定路径
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        choose();
        do
        {
            cin>>x;
            if(x==0){f=true;break;}
        } while (judges(4,x));//f==false时继续，真时重新输入。
        if(f) continue;
        output(2,x);
        }
        else if(xz==12){//输出小于x路径
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        choose();
        do
        {
            cin>>x;
            if(x==0){f=true;break;};
        } while (judges(2,x));//f==false时继续，真时重新输入。
        if(f) continue;
        output(3,x);
        }
        else if(xz==13){//输出大于x路径
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        choose();
        do
        {
            cin>>x;
            if(x==0){f=true;break;}
        } while (judges(3,x));//f==false时继续，真时重新输入。
        if(f) continue;
        output(4,x);
        }
        else if(xz==14){//输出大于x小于y路径
        if(a==NULL){ cout<<"请先创建图!!!"<<endl;continue;}
        if(head==NULL){ cout<<"不存在可行路径！！！请更换图或起或终点后再试"<<endl;continue;}
        choose();
        do
        {
            cin>>x>>y;
            if(x==0 && y==0){f=true;break;}
        } while (judges(1,x,y));//f==false时继续，真时重新输入。
        if(f) continue;
        output(5,x,y);
        }
        else if(xz==15)
        {
            dele();
            return ;   
        }
        else
        {
            cout<<"无此选项!!!"<<endl;
        }   
    }
}

int main(){
    maze a;
    a.gui();
}

/*
1 1 1 1 1 1 1 1
1 0 0 1 0 0 1 1
1 1 0 0 0 0 0 1
1 0 0 1 0 1 0 1
1 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1
*/