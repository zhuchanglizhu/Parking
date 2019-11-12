#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#include<assert.h>
#include<event.h>
#include<signal.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<mysql/mysql.h>

MYSQL mysql;
typedef struct Data
{
        char *value;//存放字符串
}Data;
typedef struct Seqlist
{
    Data *str;
    int cutsize;//当前长度，以及存放逗号个数
    int maxsize;//最大容量
}*PSeqlist,Seqlist;
PSeqlist Init_list()
{
    PSeqlist L;
    L = (PSeqlist )(malloc (sizeof(PSeqlist)));
    L->str = (Data*)(malloc(sizeof(Data)*10));
    L->str->value = (char*)(malloc(sizeof(char)*20));
    L->cutsize = 1;
    L->maxsize = 10;
    return L;
}
int IsFull(PSeqlist L)
{
    return L->cutsize > L->maxsize;
}
void Extend(PSeqlist L) //扩容
{
    L->str = (Data*)realloc(L->str,sizeof(Data)*L->cutsize);
    L->maxsize = L->cutsize;    
}
PSeqlist function(char *buff)//字符串切割
{
    PSeqlist L;
    L=Init_list();
    int i =0;
    for( ;i<strlen(buff);i++)
    {
        if(buff[i]==',')
        L->cutsize++;
    }
    if(IsFull(L))
    {
        Extend(L);
    }
    char *ptr;
    ptr = strtok(buff,",");
    i = 0;
    while(ptr != NULL)
    {
        L->str[i].value = ptr;
        i++;
        ptr = strtok(NULL,","); 
    }
    return L;
}
int create_socket();
void recv_cb(int fd,short event,void *arg);
void listen_cb(int fd,short event,void *arg);
void Admistrator_Register(int sockfd,PSeqlist L);//管理员注册
void Admistrator_Delete(int sockfd,PSeqlist L);//管理员注销
void Admistrator_Login(int sockfd,PSeqlist L);//管理员登录
void Parking_Add(int sockfd,PSeqlist L);//增加停车场
void Parking_Delete(int sockfd,PSeqlist L);//删除停车场
void Parking_Update(int sockfd,PSeqlist L);//更新停车场信息
void Parking_Select_Id(int sockfd,PSeqlist L);//根据id查询停车场信息
void Resolve(int sockfd,PSeqlist L);//解析函数


void Resolve(int sockfd,PSeqlist L)
{
     if(!strcmp(L->str[0].value,"AL"))
         Admistrator_Login(sockfd,L);//管理员登录
     if(!strcmp(L->str[0].value,"AR"))
         Admistrator_Register(sockfd,L);//管理员注册
     if(!strcmp(L->str[0].value,"AD"))
         Admistrator_Delete(sockfd,L);//管理员注销
     if(!strcmp(L->str[0].value,"PI"))
         Parking_Add(sockfd,L);    //增加停车场
     if(!strcmp(L->str[0].value,"PD"))
         Parking_Delete(sockfd,L);    // 删除停车场
     if(!strcmp(L->str[0].value,"PU"))
         Parking_Update(sockfd,L);    // 更新停车场
     if(!strcmp(L->str[0].value,"PS"))
         Parking_Select_Id(sockfd,L);  //根据Id查询停车场的信息
}

int create_socket()
{
    int sockfd = socket(AF_INET,SOCK_STREAM,0);
    if ( sockfd == -1 )
    {
        return -1;
    }

    struct sockaddr_in saddr;
    memset(&saddr,0,sizeof(saddr));
    saddr.sin_family = AF_INET;
    saddr.sin_port = htons(6000);
    saddr.sin_addr.s_addr = inet_addr("127.0.0.1");
   //设置套接字选项避免地址使用错误
   int on=1;
   int a=setsockopt(sockfd,SOL_SOCKET,SO_REUSEADDR,&on,sizeof(on));
   if(a<0)
  return -1;
    int res = bind(sockfd,(struct sockaddr*)&saddr,sizeof(saddr));
    if ( res == -1 )
    {
        return -1;
    }

    listen(sockfd,5);
    return sockfd;
}
void recv_cb(int fd,short event,void *arg)
{
    PSeqlist L;
    L=Init_list();
    char buff[128] = {0};
    int num = recv(fd,buff,127,0);
    L = function(buff);
    if(num == 0)
    {
        printf("one client over\n");
        close(fd);
        return;
    }
    Resolve(fd,L);
   
}
void listen_cb(int fd,short event,void *arg)
{
    struct event_base *base = (struct event_base*)arg;

    struct sockaddr_in caddr;
    int len = sizeof(caddr);//
    int c = accept(fd,(struct sockaddr*)&caddr,&len);
    if(c<0)
    {
	perror("accept error");
	return ;
    }
    printf("客户端描述符为 %d 的用户已连接服务器\n",c);    
    struct event *c_ev = event_new(base,c,EV_READ|EV_PERSIST,recv_cb,NULL);
    assert(c_ev != NULL);
   
   event_add(c_ev,NULL);
}
void Admistrator_Register(int sockfd,PSeqlist L)//管理员注册
{
   /* memset(buff,0,128);
    recv(fd,buff,127,0);
    const char* d=",";
    char *p_id;
    char *p_password;
    p_id=strtok(buff,d);
    p_password=strtok(NULL,d);*/
    printf("该客户端输入的电话号码为：%s",L->str[1].value);
    printf("该客户端输入的密码为:%s",L->str[2].value);
    char sql_insert[200] = {0};
    sprintf(sql_insert,"insert into P_adminstrator(P_phone,P_password) values('%s','%s');",L->str[1].value,L->str[2].value);
    //printf("%s\n",L->str[1].value);
    //printf("%s\n",L->str[2].value);
    
    int res= mysql_real_query(&mysql,sql_insert,strlen(sql_insert));
    if(res)
        send(sockfd,"Register failed",20,0);
    else
        send(sockfd,"Register sucess",20,0);
}
void Admistrator_Delete(int sockfd,PSeqlist L)//管理员注销
{
   /* memset(buff,0,128);
    recv(fd,buff,127,0);
    const char* d=",";
    char *p_id;
    char *p_password;
    p_id=strtok(buff,d);
    p_password=strtok(NULL,d);*/
    printf("该客户端输入的电话号码为：%s",L->str[1].value);
    printf("该客户端输入的密码为:%s",L->str[2].value);
    char sql_delete[200] = {0};
    sprintf(sql_delete,"delete from P_adminstrator where P_phone='%s' AND P_password='%s';",L->str[1].value,L->str[2].value);
    int res= mysql_real_query(&mysql,sql_delete,strlen(sql_delete));
    if(res)//删除数据库里面没有的数据还输出成功，所以有问题
        send(sockfd,"Delete failed",20,0);
    else
        send(sockfd,"Delete sucess",20,0);
}



void Parking_Add(int sockfd,PSeqlist L)
{
    /* memset(buff,0,128);
     recv(fd,buff,127,0);
     const char* d=",";
     char *p_name=strtok(buff,d);
     char *p_capacity=strtok(NULL,d);
     char *p_site=strtok(NULL,d);
     char *p_charge=strtok(NULL,d);
     char *p_tel=strtok(NULL,d);*/
    printf("停车场名：%s\n",L->str[1].value);
    printf("停车场容量：\n",L->str[2].value);
    printf("停车场位置：\n",L->str[3].value);
    printf("收费:\n",L->str[4].value);
    printf("电话：\n",L->str[5].value);
    
     char sql_insert[200] = {0};

     sprintf(sql_insert,"insert into P_place(P_name,P_capacity,P_site,P_charge,P_tel)values('%s','%s','%s','%s','%s');",L->str[1].value,L->str[2].value,L->str[3].value,L->str[4].value,L->str[5].value);
     int res= mysql_real_query(&mysql,sql_insert,strlen(sql_insert));
     if(res)
         send(sockfd,"Add Parking place failed",40,0);
     else
         send(sockfd,"Add Parking place sucess",40,0);
}
void Parking_Delete(int sockfd,PSeqlist L)
{
   /* memset(buff,0,128);
    recv(fd,buff,127,0);
    const char* d=",";
    char *p_name;
    p_name=strtok(buff,d);*/
    printf("该客户端输入的停车场名称为：%s",L->str[1].value);
    char sql_delete[200] = {0};
    sprintf(sql_delete,"delete from P_place where P_name='%s';",L->str[1].value);
    int res= mysql_real_query(&mysql,sql_delete,strlen(sql_delete));
    if(res)//删除数据库里面没有的数据还输出成功，所以有问题
        send(sockfd,"Delete failed",20,0);
    else
        send(sockfd,"Delete sucess",20,0);
}
void Parking_Update(int sockfd,PSeqlist L)
{
     /*memset(buff,0,128);
     recv(fd,buff,127,0);
     const char* d=",";
     char *p_oldname=strtok(buff,d);
     char *p_newname=strtok(NULL,d);*/
     //printf("该客户端输入的旧的停车场名称为：%s,新的停车场名称为:%s",L->str[1].value,L->str[2].value);
     char sql_update[200] = {0};
     sprintf(sql_update,"update P_place set P_name='%s' where P_name='%s';",L->str[2].value,L->str[1].value);
     int res= mysql_real_query(&mysql,sql_update,strlen(sql_update));
     if(res)//删除数据库里面没有的数据还输出成功，所以有问题
         send(sockfd,"update failed",20,0);
     else
         send(sockfd,"update sucess",20,0);

}

void Admistrator_Login(int sockfd,PSeqlist L)//管理员登录
{
	MYSQL_ROW row;
	MYSQL_RES *res_ptr;
	char sql_query[200]={0};//保存查询语句
   // printf("%s,%s\n",L->str[1].value,L->str[2].value);
	sprintf(sql_query,"select P_phone,P_password from P_adminstrator where P_phone='%s' and P_password=%s;",L->str[1].value,L->str[2].value);//后面不加‘’就可以查询出结果加了就错误
	int n = mysql_real_query(&mysql,sql_query,strlen(sql_query));
	if(n)
	{
     	perror("Query failed");
    }
     	else
     	{
     	res_ptr = mysql_use_result(&mysql);
     	row = mysql_fetch_row(res_ptr);
     	}
     	if(row)
     	{
     	//printf("登录成功\n");
     	printf("输出结果为:%s\n",row[1]);
     	send(sockfd,"yes",3,0);
     	}
     	else
     	{
            printf("failed\n");
     	send(sockfd,"no",2,0);
     	}
     	mysql_free_result(res_ptr);     
}
void Parking_Select_Id(int sockfd,PSeqlist L)//根据停车场名称查询信息
{
 	MYSQL_ROW row;
	MYSQL_RES *res_ptr;
     int t;
     char sql_query[200]={0};//保存查询语句
	printf("id:%s\n",L->str[1].value);
     sprintf(sql_query,"select * from P_place where  P_name='%s';",L->str[1].value);
    //查询，成功则发送yes
     int n = mysql_real_query(&mysql,sql_query,strlen(sql_query));//运行指定为计数字符串的SQL查询
     if(n)

     {
     printf("Query failed\n");
     send(sockfd,"no",2,0);
     } 
    else
     {
     res_ptr = mysql_use_result(&mysql);//初始化逐行的结果集检索
     row = mysql_fetch_row(res_ptr);//检索结果集的下一行
       
   }
	if(row)
     	{
          printf("登录成功\n");
 
          send(sockfd,"yes",3,0);
        
     	}
     	else
     	{
            printf("failed\n");
            send(sockfd,"no",2,0);
     	}
  //   char buff[128]={0};
    char a[128] = {0};
     for(t=0;t<mysql_num_fields(res_ptr);t++)
     {
         printf("%s ",row[t]);
         strcat(a,",");
	 strcat(a,(char *)row[t]);//把数据库中的数据强转
       //  sprintf(a,",%s",(char*)row[t]);
	//printf("a=%s\n",a);
     }
	send(sockfd,a,128,0);
     
     printf("\n");
 
      mysql_free_result(res_ptr);
}
int main()
{
    mysql_init(&mysql);

   if(! mysql_real_connect(&mysql,"localhost","zcl","111111","Parking",0,NULL,0))
   printf("error connect");
    else
    printf("connect ok");
    
    int sockfd = create_socket();
    if(sockfd == -1)
    {
	perror("error socket");
    }
    struct event_base *base = event_init();
    struct event *listen_ev = event_new(base,sockfd,EV_READ|EV_PERSIST,listen_cb,base);

    assert(listen_ev != NULL);

    event_add(listen_ev,NULL);
    event_base_dispatch(base);

    event_free(listen_ev);
    event_base_free(base);
    mysql_close(&mysql);
}
