.data

empty_Element:

empty_List:

empty_LL:

.text

  jal Main
  li $v0 10
  syscall

Main:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  la $a0 empty_LL
  jal LL.Start
  move $t0 $v0
  move $a0 $t0
  jal _print
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Element.Init:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  move $t2 $a2
  move $t3 $a3
  sw $t1 0($t0)
  sw $t2 4($t0)
  sw $t3 8($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Element.GetAge:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 0($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Element.GetSalary:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 4($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Element.GetMarried:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 8($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Element.Equal:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 20
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  move $s0 $a0
  move $s1 $a1
  li $s2 1
  bnez $s1 null1
  la $a0 _str0
  j _error
null1:
  move $a0 $s1
  jal Element.GetAge
  move $t0 $v0
  lw $t1 0($s0)
  move $a0 $s0
  move $a1 $t0
  move $a2 $t1
  jal Element.Compare
  move $t1 $v0
  bnez $t1 if1_else
  li $s2 0
  j if1_end
if1_else:
  bnez $s1 null2
  la $a0 _str0
  j _error
null2:
  move $a0 $s1
  jal Element.GetSalary
  move $t1 $v0
  lw $t0 4($s0)
  move $a0 $s0
  move $a1 $t1
  move $a2 $t0
  jal Element.Compare
  move $t0 $v0
  bnez $t0 if2_else
  li $s2 0
  j if2_end
if2_else:
  lw $t0 8($s0)
  beqz $t0 if3_else
  bnez $s1 null3
  la $a0 _str0
  j _error
null3:
  move $a0 $s1
  jal Element.GetMarried
  move $t0 $v0
  bnez $t0 if4_else
  li $s2 0
  j if4_end
if4_else:
if4_end:
  j if3_end
if3_else:
  bnez $s1 null4
  la $a0 _str0
  j _error
null4:
  move $a0 $s1
  jal Element.GetMarried
  move $t0 $v0
  beqz $t0 if5_else
  li $s2 0
  j if5_end
if5_else:
if5_end:
if3_end:
if2_end:
if1_end:
  move $v0 $s2
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 20
  jr $ra

Element.Compare:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a1
  move $t1 $a2
  addu $t2 $t1 1
  slt $t1 $t0 $t1
  beqz $t1 if6_else
  li $t1 0
  j if6_end
if6_else:
  slt $t2 $t0 $t2
  bnez $t2 if7_else
  li $t1 0
  j if7_end
if7_else:
  li $t1 1
if7_end:
if6_end:
  move $v0 $t1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.Init:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  li $t9 1
  sw $t9 8($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.InitNew:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  move $t2 $a2
  move $t3 $a3
  sw $t3 8($t0)
  sw $t1 0($t0)
  sw $t2 4($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.Insert:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 12
  sw $ra -4($fp)
  sw $s0 0($sp)
  move $t0 $a0
  move $t1 $a1
  move $t0 $t0
  li $a0 12
  jal _heapAlloc
  move $s0 $v0
  bnez $s0 null5
  la $a0 _str0
  j _error
null5:
  move $a0 $s0
  move $a1 $t1
  move $a2 $t0
  li $a3 0
  jal List.InitNew
  move $v0 $s0
  lw $s0 0($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 12
  jr $ra

List.SetNext:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 4($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.Delete:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 40
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  sw $s3 12($sp)
  sw $s4 16($sp)
  sw $s5 20($sp)
  sw $s6 24($sp)
  sw $s7 28($sp)
  move $t0 $a0
  move $s0 $a1
  move $s1 $t0
  li $s2 0
  li $s3 -1
  move $s4 $t0
  move $s5 $t0
  lw $s6 8($t0)
  lw $s7 0($t0)
while1_top:
  bnez $s6 ss1_else
  li $t9 1
  subu $t0 $t9 $s2
  j ss1_end
ss1_else:
  li $t0 0
ss1_end:
  beqz $t0 while1_end
  bnez $s0 null6
  la $a0 _str0
  j _error
null6:
  move $a0 $s0
  move $a1 $s7
  jal Element.Equal
  move $t0 $v0
  beqz $t0 if8_else
  li $s2 1
  slti $t0 $s3 0
  beqz $t0 if9_else
  bnez $s4 null7
  la $a0 _str0
  j _error
null7:
  move $a0 $s4
  jal List.GetNext
  move $s1 $v0
  j if9_end
if9_else:
  li $t0 -555
  move $a0 $t0
  jal _print
  bnez $s5 null8
  la $a0 _str0
  j _error
null8:
  bnez $s4 null9
  la $a0 _str0
  j _error
null9:
  move $a0 $s4
  jal List.GetNext
  move $t0 $v0
  move $a0 $s5
  move $a1 $t0
  jal List.SetNext
  li $t0 -555
  move $a0 $t0
  jal _print
if9_end:
  j if8_end
if8_else:
if8_end:
  bnez $s2 if10_else
  move $s5 $s4
  bnez $s4 null10
  la $a0 _str0
  j _error
null10:
  move $a0 $s4
  jal List.GetNext
  move $s4 $v0
  bnez $s4 null11
  la $a0 _str0
  j _error
null11:
  move $a0 $s4
  jal List.GetEnd
  move $s6 $v0
  bnez $s4 null12
  la $a0 _str0
  j _error
null12:
  move $a0 $s4
  jal List.GetElem
  move $s7 $v0
  li $s3 1
  j if10_end
if10_else:
if10_end:
  j while1_top
while1_end:
  move $v0 $s1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $s4 16($sp)
  lw $s5 20($sp)
  lw $s6 24($sp)
  lw $s7 28($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 40
  jr $ra

List.Search:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 24
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  sw $s3 12($sp)
  move $t0 $a0
  move $s0 $a1
  li $s1 0
  move $s2 $t0
  lw $s3 8($t0)
  lw $t0 0($t0)
while2_top:
  li $t9 1
  subu $t1 $t9 $s3
  beqz $t1 while2_end
  bnez $s0 null13
  la $a0 _str0
  j _error
null13:
  move $a0 $s0
  move $a1 $t0
  jal Element.Equal
  move $t1 $v0
  beqz $t1 if11_else
  li $s1 1
  j if11_end
if11_else:
if11_end:
  bnez $s2 null14
  la $a0 _str0
  j _error
null14:
  move $a0 $s2
  jal List.GetNext
  move $s2 $v0
  bnez $s2 null15
  la $a0 _str0
  j _error
null15:
  move $a0 $s2
  jal List.GetEnd
  move $s3 $v0
  bnez $s2 null16
  la $a0 _str0
  j _error
null16:
  move $a0 $s2
  jal List.GetElem
  move $t0 $v0
  j while2_top
while2_end:
  move $v0 $s1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 24
  jr $ra

List.GetEnd:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 8($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.GetElem:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 0($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.GetNext:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 4($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

List.Print:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 16
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  move $t0 $a0
  move $s0 $t0
  lw $s1 8($t0)
  lw $t0 0($t0)
while3_top:
  li $t9 1
  subu $t1 $t9 $s1
  beqz $t1 while3_end
  bnez $t0 null17
  la $a0 _str0
  j _error
null17:
  move $a0 $t0
  jal Element.GetAge
  move $t1 $v0
  move $a0 $t1
  jal _print
  bnez $s0 null18
  la $a0 _str0
  j _error
null18:
  move $a0 $s0
  jal List.GetNext
  move $s0 $v0
  bnez $s0 null19
  la $a0 _str0
  j _error
null19:
  move $a0 $s0
  jal List.GetEnd
  move $s1 $v0
  bnez $s0 null20
  la $a0 _str0
  j _error
null20:
  move $a0 $s0
  jal List.GetElem
  move $t0 $v0
  j while3_top
while3_end:
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 16
  jr $ra

LL.Start:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 24
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  sw $s3 12($sp)
  li $a0 12
  jal _heapAlloc
  move $s0 $v0
  bnez $s0 null21
  la $a0 _str0
  j _error
null21:
  move $a0 $s0
  jal List.Init
  move $s0 $s0
  bnez $s0 null22
  la $a0 _str0
  j _error
null22:
  move $a0 $s0
  jal List.Init
  bnez $s0 null23
  la $a0 _str0
  j _error
null23:
  move $a0 $s0
  jal List.Print
  li $a0 12
  jal _heapAlloc
  move $s1 $v0
  bnez $s1 null24
  la $a0 _str0
  j _error
null24:
  move $a0 $s1
  li $a1 25
  li $a2 37000
  li $a3 0
  jal Element.Init
  bnez $s0 null25
  la $a0 _str0
  j _error
null25:
  move $a0 $s0
  move $a1 $s1
  jal List.Insert
  move $s0 $v0
  bnez $s0 null26
  la $a0 _str0
  j _error
null26:
  move $a0 $s0
  jal List.Print
  li $a0 10000000
  jal _print
  li $a0 12
  jal _heapAlloc
  move $s1 $v0
  bnez $s1 null27
  la $a0 _str0
  j _error
null27:
  move $a0 $s1
  li $a1 39
  li $a2 42000
  li $a3 1
  jal Element.Init
  move $s2 $s1
  bnez $s0 null28
  la $a0 _str0
  j _error
null28:
  move $a0 $s0
  move $a1 $s1
  jal List.Insert
  move $s0 $v0
  bnez $s0 null29
  la $a0 _str0
  j _error
null29:
  move $a0 $s0
  jal List.Print
  li $a0 10000000
  jal _print
  li $a0 12
  jal _heapAlloc
  move $s1 $v0
  bnez $s1 null30
  la $a0 _str0
  j _error
null30:
  move $a0 $s1
  li $a1 22
  li $a2 34000
  li $a3 0
  jal Element.Init
  bnez $s0 null31
  la $a0 _str0
  j _error
null31:
  move $a0 $s0
  move $a1 $s1
  jal List.Insert
  move $s0 $v0
  bnez $s0 null32
  la $a0 _str0
  j _error
null32:
  move $a0 $s0
  jal List.Print
  li $a0 12
  jal _heapAlloc
  move $s3 $v0
  bnez $s3 null33
  la $a0 _str0
  j _error
null33:
  move $a0 $s3
  li $a1 27
  li $a2 34000
  li $a3 0
  jal Element.Init
  bnez $s0 null34
  la $a0 _str0
  j _error
null34:
  move $a0 $s0
  move $a1 $s2
  jal List.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null35
  la $a0 _str0
  j _error
null35:
  move $a0 $s0
  move $a1 $s3
  jal List.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  li $a0 10000000
  jal _print
  li $a0 12
  jal _heapAlloc
  move $s1 $v0
  bnez $s1 null36
  la $a0 _str0
  j _error
null36:
  move $a0 $s1
  li $a1 28
  li $a2 35000
  li $a3 0
  jal Element.Init
  bnez $s0 null37
  la $a0 _str0
  j _error
null37:
  move $a0 $s0
  move $a1 $s1
  jal List.Insert
  move $s0 $v0
  bnez $s0 null38
  la $a0 _str0
  j _error
null38:
  move $a0 $s0
  jal List.Print
  li $a0 2220000
  jal _print
  bnez $s0 null39
  la $a0 _str0
  j _error
null39:
  move $a0 $s0
  move $a1 $s2
  jal List.Delete
  move $s0 $v0
  bnez $s0 null40
  la $a0 _str0
  j _error
null40:
  move $a0 $s0
  jal List.Print
  li $a0 33300000
  jal _print
  bnez $s0 null41
  la $a0 _str0
  j _error
null41:
  move $a0 $s0
  move $a1 $s1
  jal List.Delete
  move $s0 $v0
  bnez $s0 null42
  la $a0 _str0
  j _error
null42:
  move $a0 $s0
  jal List.Print
  li $a0 44440000
  jal _print
  li $v0 0
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 24
  jr $ra

_print:
  li $v0 1   # syscall: print integer
  syscall
  la $a0 _newline
  li $v0 4   # syscall: print string
  syscall
  jr $ra

_error:
  li $v0 4   # syscall: print string
  syscall
  li $v0 10  # syscall: exit
  syscall

_heapAlloc:
  li $v0 9   # syscall: sbrk
  syscall
  jr $ra

.data
.align 0
_newline: .asciiz "\n"
_str0: .asciiz "null pointer\n"
