## Jk8s

Este projeto é um exemplo de utilização da biblioteca io.kubernetes em Java para conexão e interação com um cluster Kubernetes usando a biblioteca io.kubernetes java-client usando um crud simples em spring boot.

## Funcionalidades

* Criação do cluster EKS via Terraform
* Autenticação no cluster Kubernetes
* Listagem de namespaces

## Requisitos

* Java 8 ou superior
* Cluster Kubernetes
* Kubectl

## Como usar

1. Clone o repositório para sua máquina local
2. Abra o projeto em sua IDE Java preferida (recomendo Intellij IDEA Community)
3. Configure as credenciais de acesso ao cluster no arquivo application.properties

## Criando o cluster EKS

Observe que na pasta do projeto existe um diretório chamado Infra. Nele contém uma receita Terraform com a função de criar o cluster EKS. Para rodar a receita, basta entrar no diretório e executar os comandos abaixo:

```bash
terraform init
```

O comando `terraform init` é responsável por inicializar o Terraform. Ele baixa os plugins necessários para executar o Terraform e também baixa os módulos que estão sendo utilizados. Uma maneira de você validar o código é através do comando `Terraform validate`. Ele vai verificar se o código está correto ou não. Para executar o comando `Terraform validate`, basta executar o comando abaixo:

```bash
terraform validate
```
 Após a execução do comando terraform init, vamos executar o comando terraform plan para verificar se a receita está correta:

```bash
terraform plan -out plan
```

O comando `terraform plan -out plan` é responsável por verificar se a receita está correta. Ele vai verificar se os recursos que estão sendo criados já existem ou não. Após a execução do comando `terraform plan`, vamos executar o comando `terraform apply` para criar os recursos:

```bash
terraform apply -auto-approve
```

O comando `terraform apply -auto-approve` é responsável por criar os recursos. Após a execução do comando terraform apply. Um ponto importante aqui é no uso do parametro `-auto-approve`. Esse parâmetro é importante para que o Terraform não fique esperando a confirmação do usuário para criar os recursos. Mas, ao mesmo tempo, deve ser evitado o uso desse parâmetro em ambientes de produção. Depois do cluster criado, precisamos dar update no kubeconfig para que o kubectl consiga se comunicar com o cluster. Para isso, vamos executar o comando abaixo:

```bash
aws eks --region us-east-1 update-kubeconfig --name eks-cluster
```

E agora você já pode executar o comando `kubectl get nodes` para verificar se os nós foram criados:

```bash
kubectl get nodes
```

Para destruir o cluster criado acima, basta executar o comando abaixo:

```bash
terraform destroy -auto-approve
```


