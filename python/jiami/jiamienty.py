from Crypto.Cipher import AES
from binascii import b2a_hex, a2b_hex


def add_to_x(text,x):
    if len(text.encode('utf-8')) % x:
        add = x - (len(text.encode('utf-8')) % x)
    else:
        add = 0
    text = text + ('\0' * add)
    return text.encode('utf-8')

# 加密函数
def encrypt(text,key):
    key = add_to_x(key,32)
    mode = AES.MODE_ECB
    text = add_to_x(text,16)
    cryptos = AES.new(key, mode)

    cipher_text = cryptos.encrypt(text)
    return b2a_hex(cipher_text)


# 解密后，去掉补足的空格用strip() 去掉
def decrypt(text,key):
    key = add_to_x(key,32)
    mode = AES.MODE_ECB
    cryptor = AES.new(key, mode)
    plain_text = cryptor.decrypt(a2b_hex(text))
    return bytes.decode(plain_text).rstrip('\0')