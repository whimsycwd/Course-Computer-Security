Course-Computer-Security
========================

计算机安全课程Project

teacher:赵一鸣

Project 1-1 
6轮DES的编程实现

    加密使用64位明文和64位密钥做为输入，产生64位的密文做为输出。加解密使用相同的密钥。要求:采用CBC模式对文本加解密
    1.对话界面:选择加密或解密,输入密钥,在目录中选择明文或密文文件(内容为二进制,.txt文件,),提示加密或解密完成
    2.输出:产生密文文本或明文文本
    3.提供:说明文档,源码,可执行程序,通过加密实验,给出运行结果.
    
    DesStruct.java
    置换的数组,和沙盒等
    
    DES.java
    加解密的主要部分
    
    DesDemo.java
    简单的Swing的图形化界面
    
    
Project 1-2

    Project1-2：编程实现128bit的RSA(包括素数和密钥对产生),实现传送DES加密算法所要用的密钥，
    并与DES算法一起形成传送DES加密算法，然后用DES加密算法加密的完整体系。
    
    RSA.java
    RSA加密
    
    DesWithRSADemo.java
    简单的Swing图形化界面,Des的主密钥经过RSA加密。
    

