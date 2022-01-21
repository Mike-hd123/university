temp = 0
length=0
s = ""

def isLegal():
    global s, temp
    i=temp
    if(not temp<length):
        return False
    if(not(s[temp] == '_' or s[temp].isalpha())):
        return False

    while(i < length):
        if(s[i] == ' '):
            print("这是关键字: "+s[temp:i])
            temp=i
            return True
        if(not(s[i].isdigit() or s[i].isalpha()) or s[i]=='_'):
            return False
        i+=1
    temp=i
    return True

def isNumber():
    global s, temp
    i = temp
    statu = 0
    if(not temp < length):
        return False

    if(not(s[temp] == '-' or s[temp] == '+') and not s[temp].isdigit()):
        return False

    if(s[temp] == '-' or s[temp] == '+'):
        i+=1

    while(i<length):
        while(i<length and statu==0):
            if(s[i].isdigit()):
                pass
            elif(s[i]=='.'):
                i+=1
                statu=8
                break
            elif(s[i] == 'e'):
                i+=1
                statu=10
                break
            elif(s[i] == 'E'):
                i += 1
                statu = 10
                break
            elif(s[i] == ' '):
                print("这是实数: "+s[temp:i])
                i += 1
                temp+=i
                return True
            else:
                return False
            i+=1

        while(i<length and statu==8):
            if(s[i].isdigit()):
                pass
            elif(s[i] == 'e'):
                i += 1
                statu = 10
                break
            elif(s[i] == 'E'):
                i += 1
                statu = 10
                break
            elif(s[i] == ' '):
                print("这是实数: "+s[temp:i])
                i += 1
                temp += i
                return True
            else:
                return False
            i += 1

        if(statu == 10 and (s[i] == '+' or s[i] == '-')):
            i+=1
        
        while(i < length and statu == 10):
            if(s[i] == ' '):
                print("这是实数: "+s[temp:i])
                temp += i
                return True
            elif(not s[i].isdigit()):
                return False
            i += 1
    return True


def main():
    global s, temp,length
    while(True):
        s = input("请输入一段字符：\n")
        s += ' '
        length = len(s)
        temp=0
        while(temp < length):
            if(s[temp] == ' '):
                temp += 1
            islegal = isLegal()
            isnumber = isNumber()
            # print(isnumber)

if __name__ == '__main__':
    main()
