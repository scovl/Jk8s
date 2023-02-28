output "eks_node_group" {
  value = aws_eks_node_group.cluster
}

output "eks_node_role" {
  value = aws_iam_role.eks_nodes_roles
}