Url de configuração local:

http://localhost:8080/InPingPongOpenShift/PingPongServlet?PROXY=hostproyouip&PORT=80&USER=meuusuarioproxy&PASS=minhaenha&TIME=600000
http://localhost:8080/InPingPongOpenShift/index.html

#Para remover o proxy e a autenticação:
http://localhost:8080/InPingPongOpenShift/PingPongServlet?PROXY=UNSET&USER=UNSET

#Parametros do PingPongServlet
PROXY - host do servidor proxy (ou UNSET para desconfigurar)
PORT - porta do proxy
USER - usuario do proxy (ou UNSET se não precisar autenticar)
PASS - senha do proxy
REST - url para testar uma requisião GET  do HTTP
TIME - millesegundos de range máximo de tempo de espera aleatório para a repetição da requisição. 
