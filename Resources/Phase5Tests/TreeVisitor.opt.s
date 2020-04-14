.data

empty_TV:

empty_Tree:

vmt_Visitor:
  Visitor.visit

vmt_MyVisitor:
  MyVisitor.visit

.text

  jal Main
  li $v0 10
  syscall

Main:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  la $a0 empty_TV
  jal TV.Start
  move $t0 $v0
  move $a0 $t0
  jal _print
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

TV.Start:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 12
  sw $ra -4($fp)
  sw $s0 0($sp)
  li $a0 24
  jal _heapAlloc
  move $s0 $v0
  bnez $s0 null1
  la $a0 _str0
  j _error
null1:
  move $a0 $s0
  li $a1 16
  jal Tree.Init
  bnez $s0 null2
  la $a0 _str0
  j _error
null2:
  move $a0 $s0
  jal Tree.Print
  li $a0 100000000
  jal _print
  bnez $s0 null3
  la $a0 _str0
  j _error
null3:
  move $a0 $s0
  li $a1 8
  jal Tree.Insert
  bnez $s0 null4
  la $a0 _str0
  j _error
null4:
  move $a0 $s0
  li $a1 24
  jal Tree.Insert
  bnez $s0 null5
  la $a0 _str0
  j _error
null5:
  move $a0 $s0
  li $a1 4
  jal Tree.Insert
  bnez $s0 null6
  la $a0 _str0
  j _error
null6:
  move $a0 $s0
  li $a1 12
  jal Tree.Insert
  bnez $s0 null7
  la $a0 _str0
  j _error
null7:
  move $a0 $s0
  li $a1 20
  jal Tree.Insert
  bnez $s0 null8
  la $a0 _str0
  j _error
null8:
  move $a0 $s0
  li $a1 28
  jal Tree.Insert
  bnez $s0 null9
  la $a0 _str0
  j _error
null9:
  move $a0 $s0
  li $a1 14
  jal Tree.Insert
  bnez $s0 null10
  la $a0 _str0
  j _error
null10:
  move $a0 $s0
  jal Tree.Print
  li $a0 100000000
  jal _print
  li $a0 12
  jal _heapAlloc
  move $t0 $v0
  la $t9 vmt_MyVisitor
  sw $t9 0($t0)
  move $t0 $t0
  li $a0 50000000
  jal _print
  bnez $s0 null11
  la $a0 _str0
  j _error
null11:
  move $a0 $s0
  move $a1 $t0
  jal Tree.accept
  li $a0 100000000
  jal _print
  bnez $s0 null12
  la $a0 _str0
  j _error
null12:
  move $a0 $s0
  li $a1 24
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null13
  la $a0 _str0
  j _error
null13:
  move $a0 $s0
  li $a1 12
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null14
  la $a0 _str0
  j _error
null14:
  move $a0 $s0
  li $a1 16
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null15
  la $a0 _str0
  j _error
null15:
  move $a0 $s0
  li $a1 50
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null16
  la $a0 _str0
  j _error
null16:
  move $a0 $s0
  li $a1 12
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s0 null17
  la $a0 _str0
  j _error
null17:
  move $a0 $s0
  li $a1 12
  jal Tree.Delete
  bnez $s0 null18
  la $a0 _str0
  j _error
null18:
  move $a0 $s0
  jal Tree.Print
  bnez $s0 null19
  la $a0 _str0
  j _error
null19:
  move $a0 $s0
  li $a1 12
  jal Tree.Search
  move $t0 $v0
  move $a0 $t0
  jal _print
  li $v0 0
  lw $s0 0($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 12
  jr $ra

Tree.Init:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 8($t0)
  sw $0 12($t0)
  sw $0 16($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.SetRight:
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

Tree.SetLeft:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 0($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.GetRight:
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

Tree.GetLeft:
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

Tree.GetKey:
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

Tree.SetKey:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 8($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.GetHas_Right:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 16($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.GetHas_Left:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  lw $t0 12($t0)
  move $v0 $t0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.SetHas_Left:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 12($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.SetHas_Right:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  sw $t1 16($t0)
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.Compare:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a1
  move $t1 $a2
  addu $t2 $t1 1
  slt $t1 $t0 $t1
  beqz $t1 if1_else
  li $t1 0
  j if1_end
if1_else:
  slt $t2 $t0 $t2
  bnez $t2 if2_else
  li $t1 0
  j if2_end
if2_else:
  li $t1 1
if2_end:
if1_end:
  move $v0 $t1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.Insert:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 24
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  sw $s3 12($sp)
  move $s0 $a0
  move $s1 $a1
  li $a0 24
  jal _heapAlloc
  move $s2 $v0
  bnez $s2 null20
  la $a0 _str0
  j _error
null20:
  move $a0 $s2
  move $a1 $s1
  jal Tree.Init
  move $s0 $s0
  li $s3 1
while1_top:
  beqz $s3 while1_end
  bnez $s0 null21
  la $a0 _str0
  j _error
null21:
  move $a0 $s0
  jal Tree.GetKey
  move $t0 $v0
  slt $t0 $s1 $t0
  beqz $t0 if3_else
  bnez $s0 null22
  la $a0 _str0
  j _error
null22:
  move $a0 $s0
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 if4_else
  bnez $s0 null23
  la $a0 _str0
  j _error
null23:
  move $a0 $s0
  jal Tree.GetLeft
  move $s0 $v0
  j if4_end
if4_else:
  li $s3 0
  bnez $s0 null24
  la $a0 _str0
  j _error
null24:
  move $a0 $s0
  li $a1 1
  jal Tree.SetHas_Left
  bnez $s0 null25
  la $a0 _str0
  j _error
null25:
  move $a0 $s0
  move $a1 $s2
  jal Tree.SetLeft
if4_end:
  j if3_end
if3_else:
  bnez $s0 null26
  la $a0 _str0
  j _error
null26:
  move $a0 $s0
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if5_else
  bnez $s0 null27
  la $a0 _str0
  j _error
null27:
  move $a0 $s0
  jal Tree.GetRight
  move $s0 $v0
  j if5_end
if5_else:
  li $s3 0
  bnez $s0 null28
  la $a0 _str0
  j _error
null28:
  move $a0 $s0
  li $a1 1
  jal Tree.SetHas_Right
  bnez $s0 null29
  la $a0 _str0
  j _error
null29:
  move $a0 $s0
  move $a1 $s2
  jal Tree.SetRight
if5_end:
if3_end:
  j while1_top
while1_end:
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 24
  jr $ra

Tree.Delete:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 36
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  sw $s3 12($sp)
  sw $s4 16($sp)
  sw $s5 20($sp)
  sw $s6 24($sp)
  move $s0 $a0
  move $s1 $a1
  move $s2 $s0
  move $s3 $s0
  li $s4 1
  li $s5 0
  li $s6 1
while2_top:
  beqz $s4 while2_end
  bnez $s2 null30
  la $a0 _str0
  j _error
null30:
  move $a0 $s2
  jal Tree.GetKey
  move $t0 $v0
  slt $t1 $s1 $t0
  beqz $t1 if6_else
  bnez $s2 null31
  la $a0 _str0
  j _error
null31:
  move $a0 $s2
  jal Tree.GetHas_Left
  move $t1 $v0
  beqz $t1 if7_else
  move $s3 $s2
  bnez $s2 null32
  la $a0 _str0
  j _error
null32:
  move $a0 $s2
  jal Tree.GetLeft
  move $s2 $v0
  j if7_end
if7_else:
  li $s4 0
if7_end:
  j if6_end
if6_else:
  slt $t0 $t0 $s1
  beqz $t0 if8_else
  bnez $s2 null33
  la $a0 _str0
  j _error
null33:
  move $a0 $s2
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if9_else
  move $s3 $s2
  bnez $s2 null34
  la $a0 _str0
  j _error
null34:
  move $a0 $s2
  jal Tree.GetRight
  move $s2 $v0
  j if9_end
if9_else:
  li $s4 0
if9_end:
  j if8_end
if8_else:
  beqz $s6 if10_else
  bnez $s2 null35
  la $a0 _str0
  j _error
null35:
  move $a0 $s2
  jal Tree.GetHas_Right
  move $t0 $v0
  bnez $t0 if11_else
  bnez $s2 null36
  la $a0 _str0
  j _error
null36:
  move $a0 $s2
  jal Tree.GetHas_Left
  move $t0 $v0
  bnez $t0 if11_else
  j if11_end
if11_else:
  move $a0 $s0
  move $a1 $s3
  move $a2 $s2
  jal Tree.Remove
if11_end:
  j if10_end
if10_else:
  move $a0 $s0
  move $a1 $s3
  move $a2 $s2
  jal Tree.Remove
if10_end:
  li $s5 1
  li $s4 0
if8_end:
if6_end:
  li $s6 0
  j while2_top
while2_end:
  move $v0 $s5
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $s4 16($sp)
  lw $s5 20($sp)
  lw $s6 24($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 36
  jr $ra

Tree.Remove:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 20
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  move $s0 $a0
  move $s1 $a1
  move $s2 $a2
  bnez $s2 null37
  la $a0 _str0
  j _error
null37:
  move $a0 $s2
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 if12_else
  move $a0 $s0
  move $a1 $s1
  move $a2 $s2
  jal Tree.RemoveLeft
  j if12_end
if12_else:
  bnez $s2 null38
  la $a0 _str0
  j _error
null38:
  move $a0 $s2
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if13_else
  move $a0 $s0
  move $a1 $s1
  move $a2 $s2
  jal Tree.RemoveRight
  j if13_end
if13_else:
  bnez $s2 null39
  la $a0 _str0
  j _error
null39:
  move $a0 $s2
  jal Tree.GetKey
  move $s2 $v0
  bnez $s1 null40
  la $a0 _str0
  j _error
null40:
  move $a0 $s1
  jal Tree.GetLeft
  move $t0 $v0
  bnez $t0 null41
  la $a0 _str0
  j _error
null41:
  move $a0 $t0
  jal Tree.GetKey
  move $t0 $v0
  move $a0 $s0
  move $a1 $s2
  move $a2 $t0
  jal Tree.Compare
  move $t0 $v0
  beqz $t0 if14_else
  bnez $s1 null42
  la $a0 _str0
  j _error
null42:
  lw $t0 20($s0)
  move $a0 $s1
  move $a1 $t0
  jal Tree.SetLeft
  bnez $s1 null43
  la $a0 _str0
  j _error
null43:
  move $a0 $s1
  li $a1 0
  jal Tree.SetHas_Left
  j if14_end
if14_else:
  bnez $s1 null44
  la $a0 _str0
  j _error
null44:
  lw $t0 20($s0)
  move $a0 $s1
  move $a1 $t0
  jal Tree.SetRight
  bnez $s1 null45
  la $a0 _str0
  j _error
null45:
  move $a0 $s1
  li $a1 0
  jal Tree.SetHas_Right
if14_end:
if13_end:
if12_end:
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 20
  jr $ra

Tree.RemoveRight:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 20
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  move $s0 $a0
  move $s1 $a1
  move $s2 $a2
while3_top:
  bnez $s2 null46
  la $a0 _str0
  j _error
null46:
  move $a0 $s2
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 while3_end
  bnez $s2 null47
  la $a0 _str0
  j _error
null47:
  bnez $s2 null48
  la $a0 _str0
  j _error
null48:
  move $a0 $s2
  jal Tree.GetRight
  move $t0 $v0
  bnez $t0 null49
  la $a0 _str0
  j _error
null49:
  move $a0 $t0
  jal Tree.GetKey
  move $t0 $v0
  move $a0 $s2
  move $a1 $t0
  jal Tree.SetKey
  move $s1 $s2
  bnez $s2 null50
  la $a0 _str0
  j _error
null50:
  move $a0 $s2
  jal Tree.GetRight
  move $s2 $v0
  j while3_top
while3_end:
  bnez $s1 null51
  la $a0 _str0
  j _error
null51:
  lw $t0 20($s0)
  move $a0 $s1
  move $a1 $t0
  jal Tree.SetRight
  bnez $s1 null52
  la $a0 _str0
  j _error
null52:
  move $a0 $s1
  li $a1 0
  jal Tree.SetHas_Right
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 20
  jr $ra

Tree.RemoveLeft:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 20
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  sw $s2 8($sp)
  move $s0 $a0
  move $s1 $a1
  move $s2 $a2
while4_top:
  bnez $s2 null53
  la $a0 _str0
  j _error
null53:
  move $a0 $s2
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 while4_end
  bnez $s2 null54
  la $a0 _str0
  j _error
null54:
  bnez $s2 null55
  la $a0 _str0
  j _error
null55:
  move $a0 $s2
  jal Tree.GetLeft
  move $t0 $v0
  bnez $t0 null56
  la $a0 _str0
  j _error
null56:
  move $a0 $t0
  jal Tree.GetKey
  move $t0 $v0
  move $a0 $s2
  move $a1 $t0
  jal Tree.SetKey
  move $s1 $s2
  bnez $s2 null57
  la $a0 _str0
  j _error
null57:
  move $a0 $s2
  jal Tree.GetLeft
  move $s2 $v0
  j while4_top
while4_end:
  bnez $s1 null58
  la $a0 _str0
  j _error
null58:
  lw $t0 20($s0)
  move $a0 $s1
  move $a1 $t0
  jal Tree.SetLeft
  bnez $s1 null59
  la $a0 _str0
  j _error
null59:
  move $a0 $s1
  li $a1 0
  jal Tree.SetHas_Left
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 20
  jr $ra

Tree.Search:
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
  move $s1 $t0
  li $s2 1
  li $s3 0
while5_top:
  beqz $s2 while5_end
  bnez $s1 null60
  la $a0 _str0
  j _error
null60:
  move $a0 $s1
  jal Tree.GetKey
  move $t0 $v0
  slt $t1 $s0 $t0
  beqz $t1 if15_else
  bnez $s1 null61
  la $a0 _str0
  j _error
null61:
  move $a0 $s1
  jal Tree.GetHas_Left
  move $t1 $v0
  beqz $t1 if16_else
  bnez $s1 null62
  la $a0 _str0
  j _error
null62:
  move $a0 $s1
  jal Tree.GetLeft
  move $s1 $v0
  j if16_end
if16_else:
  li $s2 0
if16_end:
  j if15_end
if15_else:
  slt $t0 $t0 $s0
  beqz $t0 if17_else
  bnez $s1 null63
  la $a0 _str0
  j _error
null63:
  move $a0 $s1
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if18_else
  bnez $s1 null64
  la $a0 _str0
  j _error
null64:
  move $a0 $s1
  jal Tree.GetRight
  move $s1 $v0
  j if18_end
if18_else:
  li $s2 0
if18_end:
  j if17_end
if17_else:
  li $s3 1
  li $s2 0
if17_end:
if15_end:
  j while5_top
while5_end:
  move $v0 $s3
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $s2 8($sp)
  lw $s3 12($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 24
  jr $ra

Tree.Print:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $t0
  move $a0 $t0
  move $a1 $t1
  jal Tree.RecPrint
  li $v0 1
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Tree.RecPrint:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 16
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  move $s0 $a0
  move $s1 $a1
  bnez $s1 null65
  la $a0 _str0
  j _error
null65:
  move $a0 $s1
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 if19_else
  bnez $s1 null66
  la $a0 _str0
  j _error
null66:
  move $a0 $s1
  jal Tree.GetLeft
  move $t0 $v0
  move $a0 $s0
  move $a1 $t0
  jal Tree.RecPrint
  j if19_end
if19_else:
if19_end:
  bnez $s1 null67
  la $a0 _str0
  j _error
null67:
  move $a0 $s1
  jal Tree.GetKey
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s1 null68
  la $a0 _str0
  j _error
null68:
  move $a0 $s1
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if20_else
  bnez $s1 null69
  la $a0 _str0
  j _error
null69:
  move $a0 $s1
  jal Tree.GetRight
  move $t0 $v0
  move $a0 $s0
  move $a1 $t0
  jal Tree.RecPrint
  j if20_end
if20_else:
if20_end:
  li $v0 1
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 16
  jr $ra

Tree.accept:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 8
  sw $ra -4($fp)
  move $t0 $a0
  move $t1 $a1
  li $a0 333
  jal _print
  bnez $t1 null70
  la $a0 _str0
  j _error
null70:
  lw $t2 0($t1)
  lw $t2 0($t2)
  move $a0 $t1
  move $a1 $t0
  jalr $t2
  li $v0 0
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 8
  jr $ra

Visitor.visit:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 16
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  move $s0 $a0
  move $s1 $a1
  bnez $s1 null71
  la $a0 _str0
  j _error
null71:
  move $a0 $s1
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if21_else
  bnez $s1 null72
  la $a0 _str0
  j _error
null72:
  move $a0 $s1
  jal Tree.GetRight
  move $t0 $v0
  sw $t0 8($s0)
  lw $t0 8($s0)
  bnez $t0 null73
  la $a0 _str0
  j _error
null73:
  move $a0 $t0
  move $a1 $s0
  jal Tree.accept
  j if21_end
if21_else:
if21_end:
  bnez $s1 null74
  la $a0 _str0
  j _error
null74:
  move $a0 $s1
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 if22_else
  bnez $s1 null75
  la $a0 _str0
  j _error
null75:
  move $a0 $s1
  jal Tree.GetLeft
  move $t0 $v0
  sw $t0 4($s0)
  lw $t0 4($s0)
  bnez $t0 null76
  la $a0 _str0
  j _error
null76:
  move $a0 $t0
  move $a1 $s0
  jal Tree.accept
  j if22_end
if22_else:
if22_end:
  li $v0 0
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 16
  jr $ra

MyVisitor.visit:
  sw $fp -8($sp)
  move $fp $sp
  subu $sp $sp 16
  sw $ra -4($fp)
  sw $s0 0($sp)
  sw $s1 4($sp)
  move $s0 $a0
  move $s1 $a1
  bnez $s1 null77
  la $a0 _str0
  j _error
null77:
  move $a0 $s1
  jal Tree.GetHas_Right
  move $t0 $v0
  beqz $t0 if23_else
  bnez $s1 null78
  la $a0 _str0
  j _error
null78:
  move $a0 $s1
  jal Tree.GetRight
  move $t0 $v0
  sw $t0 8($s0)
  lw $t0 8($s0)
  bnez $t0 null79
  la $a0 _str0
  j _error
null79:
  move $a0 $t0
  move $a1 $s0
  jal Tree.accept
  j if23_end
if23_else:
if23_end:
  bnez $s1 null80
  la $a0 _str0
  j _error
null80:
  move $a0 $s1
  jal Tree.GetKey
  move $t0 $v0
  move $a0 $t0
  jal _print
  bnez $s1 null81
  la $a0 _str0
  j _error
null81:
  move $a0 $s1
  jal Tree.GetHas_Left
  move $t0 $v0
  beqz $t0 if24_else
  bnez $s1 null82
  la $a0 _str0
  j _error
null82:
  move $a0 $s1
  jal Tree.GetLeft
  move $t0 $v0
  sw $t0 4($s0)
  lw $t0 4($s0)
  bnez $t0 null83
  la $a0 _str0
  j _error
null83:
  move $a0 $t0
  move $a1 $s0
  jal Tree.accept
  j if24_end
if24_else:
if24_end:
  li $v0 0
  lw $s0 0($sp)
  lw $s1 4($sp)
  lw $ra -4($fp)
  lw $fp -8($fp)
  addu $sp $sp 16
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
