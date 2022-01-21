#include <iostream>
using namespace std;

int main()
{

    int total, price, pay;
    int a[] = {0, 0, 0};
    const int b[] = {50, 10, 5};
    cout << "请输入商品金额：";
    cin >> price;
    if (price > 100 || price < 0)
    {
        cout << "商品价格不能小于0或大于100" << endl;
        system("pause");
        return 0;
    }
    cout << "请输入付款金额";
    cin >> pay;
    if (pay > 100 || pay < price)
    {
        cout << "付款金额不能大于100或小于商品金额" << endl;
        system("pause");
        return 0;
    }
    total = pay - price;
    for (int i = 0; i < 3; i++)
    {
        a[i] = total / b[i];
        total -= b[i] * a[i];
    }
    cout << "50 , 10 , 5 , 1的数量为:" << endl;
    cout << a[0] << " , " << a[1] << " , " << a[2] << " , " << total << endl;
    system("pause");
    return 0;
}