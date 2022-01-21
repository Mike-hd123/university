import tkinter as tk
import pyperclip
from jiamienty import encrypt,decrypt
import sys
import os

#生成资源文件目录访问路径
def resource_path(relative_path):
    if getattr(sys, 'frozen', False): #是否Bundle Resource
        base_path = sys._MEIPASS
    else:
        base_path = os.path.abspath(".")
    return os.path.join(base_path, relative_path)

window = tk.Tk()
window.title('加解密程序')
window.geometry('450x400')

key = tk.StringVar()
value = tk.StringVar()
answer = tk.StringVar()
choose = tk.StringVar()

canvas = tk.Canvas(window,height=200,width=500)
image_file = tk.PhotoImage(file=resource_path(os.path.join('res','welcome.gif')))
image = canvas.create_image(0,0,anchor='nw',image=image_file)
canvas.pack()

choose.set('M')

tk.Radiobutton(window,text='加密',variable=choose,value='M').place(x=170,y=120)
tk.Radiobutton(window,text='解密',variable=choose,value='J').place(x=270,y=120)

tk.Label(window,text='密钥:').place(x=50,y=150)
tk.Label(window,text='内容:').place(x=50,y=190)

tk.Entry(window,textvariable=key,show='*',width=35).place(x=130,y=150)
tk.Entry(window,textvariable=value,show='*',width=35).place(x=130,y=190)


def commit():
    if(choose.get()=='M'):
        answer.set(encrypt(value.get(),key.get()).decode('utf-8'))
    else:
        answer.set(decrypt(value.get().encode("utf-8"),key.get()))

def copy():
    pyperclip.copy(answer.get())

tk.Button(window,text='提交',command=commit).place(x=170,y=230)
tk.Button(window,text='复制',command=copy).place(x=270,y=230)

tk.Message(window,textvariable=answer,width=400).place(x=25,y=300)

window.mainloop()