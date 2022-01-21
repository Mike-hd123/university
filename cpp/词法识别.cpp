#include <iostream>
using namespace std;

char s[10000];
int temp = 0, length = 0; //当前的字符位置

void writeword(int a, int start, int end); //输出字符串

bool isLetter(char c)
{
    return (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'));
}

bool isNumber(char c)
{
    return ('0' <= c && c <= '9');
}

bool isRealNumber()
{
    int i = temp;
    int staut = 0;
    if (!(s[temp] == '-' || s[temp] == '+') && !isNumber(s[temp]))
        return 0;

    if (s[temp] == '-' || s[temp] == '+') //有符号扫描下一个，从数字开始
        i++;

    for (; i < length; i++)
    {
        //识别整数
        while (i < length && staut == 0)
        {
            // cout << staut << ' ' << s[i] << ' ' << i << endl;
            if (isNumber(s[i]))
                ;
            else
                switch (s[i])
                {
                case '.':
                    staut = 8;
                    break;
                case 'e':
                    staut = 10;
                    break;
                case 'E':
                    staut = 10;
                    break;
                case ' ':
                    writeword(0, temp, i);
                    temp = ++i;
                    return 1;
                    break;
                default:
                    return 0;
                    break;
                }

            i++;
        }
        //识别小数
        while (i < length && staut == 8)
        {
            // cout << staut << ' ' << s[i] << ' ' << i << endl;
            if (isNumber(s[i]))
                ;
            else
                switch (s[i])
                {
                case 'e':
                    staut = 10;
                    break;
                case 'E':
                    staut = 10;
                    break;
                case ' ':
                    writeword(0, temp, i);
                    temp = ++i;
                    return 1;
                    break;
                default:
                    return 0;
                    break;
                }
            i++;
        }
        //识别科学计数法
        if (staut == 10 && (s[i] == '+' || s[i] == '-'))
            i++;
            
        while (i < length && staut == 10)
        {
            if (s[i] == ' ')
            {
                writeword(0, temp, i);
                temp = ++i;
                return 1;
            }
            else if(!isNumber(s[i]))
                return 0;
            i++;
        }
    }
    return 1;
}

bool isLegal()
{
    if (!(s[temp] == '_' || isLetter(s[temp])))
        return 0;
    for (int i = temp; i < length; i++)
    {
        if (s[i] == ' ')
        {
            writeword(1, temp, i);
            temp = ++i;
            return 1;
        }
        if (!(isLetter(s[i]) || isNumber(s[i]) || s[i] == '_'))
            return 0;
    }
    // 到结尾
    temp = length;
    return 1;
}

void search()
{
    bool isnumber = false, islegal = false;
    while (temp < length)
    {
        if (s[temp] == ' ') //清除多余的空格
            temp++;
        islegal = isLegal();
        isnumber = isRealNumber();
        if (!islegal && !isnumber)
            for (int i = temp; i < length; i++)
                if (s[i] == ' ')
                {
                    writeword(2, temp, i++);
                    temp = i;
                    break;
                }
    }
}

void input()
{
    char temp;
    while (1)
    {
        temp = cin.get();
        if (temp != '\n')
            s[length++] = temp;
        else
        {
            s[length++] = ' ';
            break;
        }
    }
}

void writeword(int a, int start, int end)
{
    if (a == 1)
        cout << "这是关键字: ";
    else if (a == 0)
        cout << "这是实数: ";
    else
        cout << "未识别字符: ";
    for (; start < end; start++)
    {
        cout << s[start];
    }
    cout << endl;
}

int main()
{
    do
    {
        cout << "请输入一段代码(结束码EOF):" << endl;
        input();
        search();
        length = 0;
        temp = 0;
    } while (!(s[0] == 'E' && s[1] == 'O' && s[2] == 'F'));
    return 0;
}