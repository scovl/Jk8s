output "eks_node_group" {
  value = aws_eks_node_group.cluster
}

output "eks_node_role" {
  value = aws_iam_role.eks_nodes_roles
}

output "eks_node_sg" {
  value = aws_security_group.eks_node_group
}

output "eks_node_sg_id" {
  value = aws_security_group.eks_node_group.id
}