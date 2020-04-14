.data

empty_Fac:

.text

  jal Main
  li $v0 10
  syscall

Main:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  la $a0 empty_Fac
  li $a1 10
  jal Fac.ComputeFac
  move $t0 $v0
  move $a0 $t0
  jal _print
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Fac.ComputeFac:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 12
  sw $ra -4($fp)
  sw $s0 0($sp)
  move $t0 $a0
  move $s0 $a1
  slti $t1 $s0 1
  beqz $t1 if1_else
  li $t1 1
  j if1_end
if1_else:
  subu $t2 $s0 1
  move $a0 $t0
  move $a1 $t2
  jal Fac.ComputeFac
  move $t2 $v0
  mul $t1 $s0 $t2
if1_end:
  move $v0 $t1
  lw $s0 0($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 12
  jr $ra

_print:
  li $v0 1   # syscall: print integer
  syscall
  la $a0 _newline
  li $v0 4   # syscall: print string
  syscall
  jr $ra

.data
.align 0
_newline: .asciiz "\n"
