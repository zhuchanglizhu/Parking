#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <assert.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

//Admistrator_Login(int sockfd);//管理员登
void Admistrator_Register(int sockfd);//管理员注册
void Admistrator_Delete(int sockfd);//管理员注销
void Parking_Add(int sockfd);    //增加停车场
void Parking_Delete(int sockfd);    // 删除停车场
void Parking_Update(int sockfd);    // 更新停车场
//Parking_Select_Id(int sockfd);  //根据Id查询停车场的信息

void  Admistrator_Register(int sockfd)//管理员注册
 {
     char *username = (char*)malloc(sizeof(char)*20);
     char *password = (char*)malloc(sizeof(char)*20);
     printf("请输入用户电话号码：");
     scanf("%s",username);
     printf("请输入密码：");
     scanf("%s",password);
     char buff1[200]={0};
     sprintf(buff1,"AR,%s,%s",username,password);
     send(sockfd,buff1,strlen(buff1),0);
     printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     printf("ser:%s\n",buff2);
     if(strncmp(buff2,"yes",3)==0)
     {
         printf("登录成功！\n");
        // return 1;
     }
     if(strncmp(buff2,"no",2)==0)
     {
         printf("帐号或密码错误，请重新选择！\n");
        // return 0;
     }
     free(username);
     free(password);
 }

void  Admistrator_Delete(int sockfd)//管理员注销
 {
     char *username = (char*)malloc(sizeof(char)*20);
     char *password = (char*)malloc(sizeof(char)*20);
     printf("请输入用户电话号码：");
     scanf("%s",username);
     printf("请输入密码：");
     scanf("%s",password);
     char buff1[200]={0};
     sprintf(buff1,"AD,%s,%s",username,password);
     send(sockfd,buff1,strlen(buff1),0);
    // printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     printf("ser:%s\n",buff2);
     free(username);
     free(password);
 }

int  Admistrator_Login(int sockfd)//管理员登录
 {
     char *username = (char*)malloc(sizeof(char)*20);
     char *password = (char*)malloc(sizeof(char)*20);
     printf("请输入用户电话号码：");
     scanf("%s",username);
     printf("请输入密码：");
     scanf("%s",password);
     char buff1[200]={0};
     sprintf(buff1,"AL,%s,%s",username,password);
     send(sockfd,buff1,strlen(buff1),0);
    // printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     //printf("ser:%s\n",buff2);
     if(strncmp(buff2,"yes",3)==0)
     {
         printf("登录成功！\n");
         return 1;
     }
     if(strncmp(buff2,"no",2)==0)
     {
         printf("电话号或密码错误，请重新选择！\n");
         return 0;
     }
     free(username);
     free(password);
 }


void Parking_Select_Id(int sockfd) //根据名字查询停车场的信息
{
     char *p_name= (char*)malloc(sizeof(char)*20);
     printf("请输入用户名：");
     scanf("%s",p_name);
     char buff1[200]={0};
     sprintf(buff1,"PS,%s",p_name);
     send(sockfd,buff1,strlen(buff1),0);
    // printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);//判断数据库是否查询成功
     char *p=strtok(buff2,",");
     printf("%s\n",p);
     p=strtok(NULL,",");
     while(p!=NULL)
    {
       printf("%s ",p);
       p=strtok(NULL,",");
    }
     printf("\n");
     
    //  printf("ser:%s\n",buff2);
 
     if(strncmp(buff2,"yes",3)==0)
     {
        // printf("查询成功！\n");
       //   memset(buff2,0,128);
       // recv(sockfd,buff2,127,0);
      //  printf("%s\n",buff2);
        // char buff3[128]={0};
        // recv(sockfd,buff3,127,0);
        // char*s=strtok(s," ");
        // while(s!=NULL)
      /*  {
          printf("%s ",s);
          s=strtok(NULL," ");
        }*/
       // printf("%s",buff3);

     }
     if(strncmp(buff2,"no",2)==0)
     {
         printf("帐号错误，请重新选择！\n");
     }
    // char buff3[128]={0};
    // int a = recv(sockfd,buff3,127,0);

     //printf("%s\n",buff3);
     free(p_name);
}


void  Parking_Increase(int sockfd)//增加停车场信息
 {
     char *username = (char*)malloc(sizeof(char)*20);
     char *capacity = (char*)malloc(sizeof(char)*20);
     char *site = (char*)malloc(sizeof(char)*20);
     char *charge = (char*)malloc(sizeof(char)*20);
     char *tel = (char*)malloc(sizeof(char)*20);
     printf("请输入停车场名称：");
     scanf("%s",username);
     printf("请输入可容纳车辆：");
     scanf("%s",capacity);
     printf("请输入位置：");
     scanf("%s",site);
     printf("请输入收费标准：");
     scanf("%s",charge);
     printf("请输入联系方式：");
     scanf("%s",tel);
     char buff1[200]={0};
     sprintf(buff1,"PI,%s,%s,%s,%s,%s",username,capacity,site,charge,tel);
     send(sockfd,buff1,strlen(buff1),0);
     printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     printf("ser:%s\n",buff2);
     free(username);
     free(capacity);
     free(site);
     free(charge);
     free(tel);
 }

void Parking_Delete(int sockfd)   // 删除停车场
{
     char *username = (char*)malloc(sizeof(char)*20);
     printf("请输入停车场名称：");
     scanf("%s",username);
     char buff1[200]={0};
     sprintf(buff1,"PD,%s",username);
     send(sockfd,buff1,strlen(buff1),0);
     printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     printf("ser:%s\n",buff2);
     free(username);
}

void Parking_Update(int sockfd)//更新停车场
{
   char *oldname = (char*)malloc(sizeof(char)*20);
   char *newname = (char*)malloc(sizeof(char)*20);
   printf("请输入停车场旧名称：");
   scanf("%s",oldname);
   printf("请输入停车场新名称：");
   scanf("%s",newname);
   char buff1[128]={0};
   sprintf(buff1,"PU,%s,%s",oldname,newname);
     send(sockfd,buff1,strlen(buff1),0);
     printf("%s\n",buff1);
     char buff2[128]={0};
     recv(sockfd,buff2,127,0);
     printf("ser:%s\n",buff2);
     free(oldname);
     free(newname);  
}

int main()
{
    int sockfd = socket(AF_INET,SOCK_STREAM,0);
    assert( sockfd != -1 );
    struct sockaddr_in saddr;
    memset(&saddr,0,sizeof(saddr));
    saddr.sin_family = AF_INET;
    saddr.sin_port = htons(6000);//1024 
    saddr.sin_addr.s_addr = inet_addr("127.0.0.1");
    int res = connect(sockfd,(struct sockaddr*)&saddr,sizeof(saddr));
    assert( res != -1 );
    char buff[128] = {0};
    while(1)
    {
    setbuf(stdin,NULL);
    printf("********************************************\n");
    printf("输入---------------功能---------------------\n");
    printf("1-----------停车场管理员注册------------\n");
    printf("2-----------停车场管理员登录--------------\n");
    printf("3-----------管理员注销------------\n");
    printf("end---------退出管理员界面----------\n");
    
    fgets(buff,128,stdin);
    setbuf(stdin,NULL);
   // send(sockfd,buff,strlen(buff),0);
    if(strncmp(buff,"1",1)==0)
    {
        Admistrator_Register(sockfd);

    }
    if(strncmp(buff,"3",1)==0)
    {
      Admistrator_Delete(sockfd);
    // if(n==1)
    // printf("登录成功");
   //  else
     
    }


   if(strncmp(buff,"2",1)==0)
    {
	int n=Admistrator_Login(sockfd);
	if(n==1)
        {
   
     

 while(1)
        {
            printf("-------*欢迎进入停车场界面*-------\n");
            printf("1-------增加停车场信息-----------\n");
            printf("2-------修改停车场信息-----------\n");
            printf("3----根据停车场名字查询停车场信息---\n");
            printf("4------删除停车场信息------------\n");
            printf("bye----退出此界面---------------\n");
            setbuf(stdin,NULL);
            memset(buff,0,128);
            fgets(buff,128,stdin);
            send(sockfd,buff,strlen(buff),0);
            if(strncmp(buff,"1",1)==0)
            {
               Parking_Increase(sockfd);
            }
            
            if(strncmp(buff,"2",1)==0)
            {
               Parking_Update(sockfd);
            }
            if(strncmp(buff,"3",1)==0)
            {
	       Parking_Select_Id(sockfd);
              
            }

            if(strncmp(buff,"4",1)==0)
            {
                Parking_Delete(sockfd);
            }
           
            if(strncmp(buff,"bye",3)==0)
            {
                break;
            }
        }
        }
       }
    
    if ( strncmp(buff,"end",3) == 0 )
    { 
        break;
     }
   // send(sockfd,buff,strlen(buff),0);
    //memset(buff,0,128);
   // recv(sockfd,buff,127,0);
   // printf("buff=%s\n",buff);
 }
    close(sockfd);
    exit(0);
}
