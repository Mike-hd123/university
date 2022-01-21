#include <iostream>
#include <stdio.h>
using namespace std;

int wishAge;
int daynum, leaveDaymun = 0, temp;
int m[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //存放平年各月份天数的数组；
int n[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //存放闰年各月份天数的数组；

bool check(int a, int b, int c) //判断输入的年月日是否合法；
{
    if (a < 1900 || a > 2100 || b < 1 || b > 12 || c < 1 || c > 31) //数据的边界值；
        return false;
    else
        return true;
}

bool leapYear(int a) //判断是否为闰年；
{
    if ((a % 4 == 0 && a % 100 != 0) || a % 400 == 0) //闰年的条件；
        return true;
    else
        return false;
}

void countLiveday(int a, int b, int c, int a1, int b1, int c1)
{
    daynum = 0; //变量的初始化，否则每次调用函数，daynum的值就会继续累加，计算错误！！！
    int t1 = a, t2 = a1;
    for (; a1 < a; a1++) //计算年天数；
    {
        if (leapYear(a1) == 1)
            daynum += 366; //闰年天数；
        else
            daynum += 365; //平年天数；
    }

    for (int i = 0; i < b1 - 1; i++) //计算出生前没过的月天数；
    {
        if (leapYear(t2) == 1)
            daynum -= n[i]; //二月有29天；
        else
            daynum -= m[i]; //二月有28天；
    }

    for (int j = 0; j < b - 1; j++) //计算今年没算的天数；
    {
        if (leapYear(t1) == 1)
            daynum += n[j]; //二月有29天
        else
            daynum += m[j]; //二月有28天；
    }
    daynum = daynum - c1 + c; //减去出生那个月没过的天数，加上现在这个月已过的天数；
}

void imputernowdata(int *y, int *m, int *d)
{
    int ret;
    bool ok = false;
    cout << "下面输入的日期格式为yyyy-mm-dd" << endl;
    do
    {
        cout << "请输入今天的日期：";
        ret = scanf("%d-%d-%d", y, m, d); //输入现在日期
        if (ret != 3)
        {
            cout << "请按照要求格式输入" << endl;
            while (getchar() != '\n')
                ;
        }
        else
        {
            ok = check(*y, *m, *d);
            if (!ok)
                cout << "输入日期不合理！！！" << endl;
        }
    } while (!ok || ret != 3);
}

int main()
{
    int y1, y2, m1, m2, d1, d2, ret;
    bool ok = false;
    imputernowdata(&y1, &m1, &d1);
    do
    {
        cout
            << "请输入你的出生日期：";
        ret = scanf("%d-%d-%d", &y2, &m2, &d2); //输入出生日期；
        if (ret != 3)
        {
            cout << "请按照要求格式输入" << endl;
            while (getchar() != '\n')
                ;
        }
        else
        {
            ok = check(y2, m2, d2);
            if (!ok)
                cout << "输入日期不合理！！！" << endl;
        }

        if (ok && y1 < y2) //日期合法性的判断；
        {
            cout << "出生年月小于现在年月，输入错误！！！" << endl;
            ok = false;
        }
    } while (!ok || ret != 3);

    countLiveday(y1, m1, d1, y2, m2, d2);     //日期输入正确，调用函数，计算已经活过的天数；
    cout << "你已经活了" << daynum << "天。"; //输出天数daynum；
    cout << endl;

    cout << "请输入您的期望年龄（ >你的真实年龄 ）：";
    cin >> wishAge;        //输出期望年龄wishAge;
    if (wishAge < y1 - y2) //期望年龄合法性判断；
    {
        cout << "输入的期望年龄不合理！！！";
    }
    else //当数据合法时；
    {
        temp = daynum;                                  //保存上一步得到的daynum，因为再次调用函数时会被覆盖掉；
        countLiveday(y2 + wishAge, m1, d1, y2, m2, d2); //计算从出生到期望年龄要活的总天数；
        leaveDaymun = daynum - temp;                    //还要活的天数=要活的总天数-已经活的天数；
        cout << "你还要活" << leaveDaymun << "天。" << endl;
    }
    return 0;
}
