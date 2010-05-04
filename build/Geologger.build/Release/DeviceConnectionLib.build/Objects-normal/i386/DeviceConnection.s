	.cstring
LC0:
	.ascii "(I)V\0"
LC1:
	.ascii "deviceStateEvent\0"
LC2:
	.ascii "r+\0"
	.text
.globl _Java_DeviceConnection_open
_Java_DeviceConnection_open:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$36, %esp
	call	L6
"L00000000001$pb":
L6:
	popl	%ebx
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	676(%eax), %edx
	movl	$0, 8(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -20(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	124(%eax), %edx
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -16(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	132(%eax), %edx
	leal	LC0-"L00000000001$pb"(%ebx), %eax
	movl	%eax, 12(%esp)
	leal	LC1-"L00000000001$pb"(%ebx), %eax
	movl	%eax, 8(%esp)
	movl	-16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -12(%ebp)
	leal	LC2-"L00000000001$pb"(%ebx), %eax
	movl	%eax, 4(%esp)
	movl	-20(%ebp), %eax
	movl	%eax, (%esp)
	call	L_fopen$stub
	movl	%eax, %edx
	leal	_serial-"L00000000001$pb"(%ebx), %eax
	movl	%edx, (%eax)
	leal	_serial-"L00000000001$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	jne	L2
	leal	_state-"L00000000001$pb"(%ebx), %eax
	movl	$-1, (%eax)
	jmp	L4
L2:
	leal	_state-"L00000000001$pb"(%ebx), %eax
	movl	$1, (%eax)
L4:
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000001$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	680(%eax), %edx
	movl	-20(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	addl	$36, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_puts
_Java_DeviceConnection_puts:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$36, %esp
	call	L12
"L00000000002$pb":
L12:
	popl	%ebx
	leal	_serial-"L00000000002$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L11
	leal	_state-"L00000000002$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	jle	L11
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	676(%eax), %edx
	movl	$0, 8(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -12(%ebp)
	leal	_serial-"L00000000002$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	L_fputs$UNIX2003$stub
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	680(%eax), %edx
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
L11:
	addl	$36, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_gets
_Java_DeviceConnection_gets:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$8212, %esp
	call	L19
"L00000000003$pb":
L19:
	popl	%ebx
	leal	_serial-"L00000000003$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L18
	leal	_state-"L00000000003$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$1, %eax
	jne	L18
	leal	_serial-"L00000000003$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 8(%esp)
	movl	$8192, 4(%esp)
	leal	-8200(%ebp), %eax
	movl	%eax, (%esp)
	call	L_fgets$stub
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	668(%eax), %edx
	leal	-8200(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
L18:
	addl	$8212, %esp
	popl	%ebx
	leave
	ret
	.cstring
LC3:
	.ascii "(ILjava/lang/String;)V\0"
LC4:
	.ascii "deviceDataEvent\0"
	.text
.globl _Java_DeviceConnection_startGetsAsync
_Java_DeviceConnection_startGetsAsync:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%edi
	pushl	%esi
	pushl	%ebx
	subl	$8268, %esp
	call	L37
"L00000000004$pb":
L37:
	popl	%ebx
	leal	_serial-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L35
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$1, %eax
	jne	L35
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	124(%eax), %edx
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -36(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	132(%eax), %edx
	leal	LC0-"L00000000004$pb"(%ebx), %eax
	movl	%eax, 12(%esp)
	leal	LC1-"L00000000004$pb"(%ebx), %eax
	movl	%eax, 8(%esp)
	movl	-36(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -32(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	132(%eax), %edx
	leal	LC3-"L00000000004$pb"(%ebx), %eax
	movl	%eax, 12(%esp)
	leal	LC4-"L00000000004$pb"(%ebx), %eax
	movl	%eax, 8(%esp)
	movl	-36(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -28(%ebp)
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	$2, (%eax)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-32(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	jmp	L36
L25:
	leal	_serial-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 8(%esp)
	movl	$8192, 4(%esp)
	leal	-8228(%ebp), %eax
	movl	%eax, (%esp)
	call	L_fgets$stub
	testl	%eax, %eax
	je	L24
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %esi
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	668(%eax), %edx
	leal	-8228(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, %edx
	leal	-8228(%ebp), %eax
	movl	$-1, %ecx
	movl	%eax, -8236(%ebp)
	movl	$0, %eax
	cld
	movl	-8236(%ebp), %edi
	repnz
	scasb
	movl	%ecx, %eax
	notl	%eax
	decl	%eax
	movl	%edx, 16(%esp)
	movl	%eax, 12(%esp)
	movl	-28(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%esi
L24:
L36:
	leal	_serial-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_feof$stub
	testl	%eax, %eax
	jne	L27
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$2, %eax
	je	L25
L27:
	leal	_serial-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_feof$stub
	testl	%eax, %eax
	je	L29
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	$-3, (%eax)
	jmp	L31
L29:
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$-2, %eax
	jne	L32
	leal	_serial-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_fclose$stub
	jmp	L31
L32:
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$1, %eax
	je	L31
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	$-4, (%eax)
L31:
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000004$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-32(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
L35:
	addl	$8268, %esp
	popl	%ebx
	popl	%esi
	popl	%edi
	leave
	ret
.globl _Java_DeviceConnection_endGetsAsync
_Java_DeviceConnection_endGetsAsync:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$36, %esp
	call	L43
"L00000000005$pb":
L43:
	popl	%ebx
	leal	_serial-"L00000000005$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L42
	leal	_state-"L00000000005$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$2, %eax
	jne	L42
	leal	_state-"L00000000005$pb"(%ebx), %eax
	movl	$1, (%eax)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	124(%eax), %edx
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -16(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	132(%eax), %edx
	leal	LC0-"L00000000005$pb"(%ebx), %eax
	movl	%eax, 12(%esp)
	leal	LC1-"L00000000005$pb"(%ebx), %eax
	movl	%eax, 8(%esp)
	movl	-16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -12(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000005$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
L42:
	addl	$36, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_getBaud
_Java_DeviceConnection_getBaud:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$100, %esp
	call	L74
"L00000000006$pb":
L74:
	popl	%ebx
	leal	_serial-"L00000000006$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L45
	leal	_state-"L00000000006$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	jle	L45
	movl	$0, -16(%ebp)
	leal	_serial-"L00000000006$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_fileno$stub
	movl	%eax, -12(%ebp)
	leal	-60(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	L_tcgetattr$stub
	leal	-60(%ebp), %eax
	movl	%eax, (%esp)
	call	L_cfgetispeed$stub
	movl	%eax, -16(%ebp)
	movl	-16(%ebp), %eax
	movl	%eax, -84(%ebp)
	cmpl	$1200, -84(%ebp)
	je	L56
	cmpl	$1200, -84(%ebp)
	jg	L66
	cmpl	$134, -84(%ebp)
	je	L51
	cmpl	$134, -84(%ebp)
	jg	L67
	cmpl	$75, -84(%ebp)
	je	L49
	cmpl	$110, -84(%ebp)
	je	L50
	cmpl	$50, -84(%ebp)
	je	L48
L45:
	jmp	L44
L67:
	cmpl	$200, -84(%ebp)
	je	L53
	cmpl	$200, -84(%ebp)
	jg	L68
	cmpl	$150, -84(%ebp)
	je	L52
	jmp	L45
L68:
	cmpl	$300, -84(%ebp)
	je	L54
	cmpl	$600, -84(%ebp)
	je	L55
	jmp	L45
L66:
	cmpl	$19200, -84(%ebp)
	je	L61
	cmpl	$19200, -84(%ebp)
	jg	L69
	cmpl	$2400, -84(%ebp)
	je	L58
	cmpl	$2400, -84(%ebp)
	jg	L70
	cmpl	$1800, -84(%ebp)
	je	L57
	jmp	L45
L70:
	cmpl	$4800, -84(%ebp)
	je	L59
	cmpl	$9600, -84(%ebp)
	je	L60
	jmp	L45
L69:
	cmpl	$57600, -84(%ebp)
	je	L63
	cmpl	$57600, -84(%ebp)
	jg	L71
	cmpl	$38400, -84(%ebp)
	je	L62
	jmp	L45
L71:
	cmpl	$115200, -84(%ebp)
	je	L64
	cmpl	$230400, -84(%ebp)
	je	L65
	jmp	L45
L48:
	movl	$50, -76(%ebp)
L72:
	movl	-76(%ebp), %eax
	movl	%eax, -80(%ebp)
	jmp	L44
L49:
	movl	$75, -76(%ebp)
	jmp	L72
L50:
	movl	$110, -76(%ebp)
	jmp	L72
L51:
	movl	$134, -76(%ebp)
	jmp	L72
L52:
	movl	$150, -76(%ebp)
	jmp	L72
L53:
	movl	$200, -76(%ebp)
	jmp	L72
L54:
	movl	$300, -76(%ebp)
	jmp	L72
L55:
	movl	$600, -76(%ebp)
	jmp	L72
L56:
	movl	$1200, -76(%ebp)
	jmp	L72
L57:
	movl	$1800, -76(%ebp)
	jmp	L72
L58:
	movl	$2400, -76(%ebp)
	jmp	L72
L59:
	movl	$4800, -76(%ebp)
	jmp	L72
L60:
	movl	$9600, -76(%ebp)
	jmp	L72
L61:
	movl	$19200, -76(%ebp)
	jmp	L72
L62:
	movl	$38400, -76(%ebp)
	jmp	L72
L63:
	movl	$57600, -76(%ebp)
	jmp	L72
L64:
	movl	$115200, -76(%ebp)
	jmp	L72
L65:
	movl	$230400, -76(%ebp)
	jmp	L72
L44:
	movl	-80(%ebp), %eax
	addl	$100, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_setBaud
_Java_DeviceConnection_setBaud:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$100, %esp
	call	L104
"L00000000007$pb":
L104:
	popl	%ebx
	leal	_serial-"L00000000007$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L103
	leal	_state-"L00000000007$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$1, %eax
	jne	L103
	movl	$0, -16(%ebp)
	movl	16(%ebp), %eax
	movl	%eax, -76(%ebp)
	cmpl	$1200, -76(%ebp)
	je	L88
	cmpl	$1200, -76(%ebp)
	jg	L97
	cmpl	$134, -76(%ebp)
	je	L83
	cmpl	$134, -76(%ebp)
	jg	L98
	cmpl	$75, -76(%ebp)
	je	L81
	cmpl	$110, -76(%ebp)
	je	L82
	cmpl	$50, -76(%ebp)
	je	L80
L79:
	movl	$38400, -16(%ebp)
	jmp	L102
L98:
	cmpl	$200, -76(%ebp)
	je	L85
	cmpl	$200, -76(%ebp)
	jg	L99
	cmpl	$150, -76(%ebp)
	je	L84
	jmp	L79
L99:
	cmpl	$300, -76(%ebp)
	je	L86
	cmpl	$600, -76(%ebp)
	je	L87
	jmp	L79
L97:
	cmpl	$9600, -76(%ebp)
	je	L92
	cmpl	$9600, -76(%ebp)
	jg	L100
	cmpl	$2400, -76(%ebp)
	je	L90
	cmpl	$4800, -76(%ebp)
	je	L91
	cmpl	$1800, -76(%ebp)
	je	L89
	jmp	L79
L100:
	cmpl	$57600, -76(%ebp)
	je	L94
	cmpl	$57600, -76(%ebp)
	jg	L101
	cmpl	$19200, -76(%ebp)
	je	L93
	jmp	L79
L101:
	cmpl	$115200, -76(%ebp)
	je	L95
	cmpl	$230400, -76(%ebp)
	je	L96
	jmp	L79
L80:
	movl	$50, -16(%ebp)
	jmp	L102
L81:
	movl	$75, -16(%ebp)
	jmp	L102
L82:
	movl	$110, -16(%ebp)
	jmp	L102
L83:
	movl	$134, -16(%ebp)
	jmp	L102
L84:
	movl	$150, -16(%ebp)
	jmp	L102
L85:
	movl	$200, -16(%ebp)
	jmp	L102
L86:
	movl	$300, -16(%ebp)
	jmp	L102
L87:
	movl	$600, -16(%ebp)
	jmp	L102
L88:
	movl	$1200, -16(%ebp)
	jmp	L102
L89:
	movl	$1800, -16(%ebp)
	jmp	L102
L90:
	movl	$2400, -16(%ebp)
	jmp	L102
L91:
	movl	$4800, -16(%ebp)
	jmp	L102
L92:
	movl	$9600, -16(%ebp)
	jmp	L102
L93:
	movl	$19200, -16(%ebp)
	jmp	L102
L94:
	movl	$57600, -16(%ebp)
	jmp	L102
L95:
	movl	$115200, -16(%ebp)
	jmp	L102
L96:
	movl	$230400, -16(%ebp)
L102:
	leal	_serial-"L00000000007$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_fileno$stub
	movl	%eax, -12(%ebp)
	leal	-60(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	L_tcgetattr$stub
	movl	-16(%ebp), %eax
	movl	%eax, 4(%esp)
	leal	-60(%ebp), %eax
	movl	%eax, (%esp)
	call	L_cfsetispeed$stub
	movl	-16(%ebp), %eax
	movl	%eax, 4(%esp)
	leal	-60(%ebp), %eax
	movl	%eax, (%esp)
	call	L_cfsetospeed$stub
	leal	-60(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	$0, 4(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	L_tcsetattr$stub
L103:
	addl	$100, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_close
_Java_DeviceConnection_close:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$36, %esp
	call	L113
"L00000000008$pb":
L113:
	popl	%ebx
	leal	_serial-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	je	L112
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	testl	%eax, %eax
	jle	L112
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	124(%eax), %edx
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -16(%ebp)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	132(%eax), %edx
	leal	LC0-"L00000000008$pb"(%ebx), %eax
	movl	%eax, 12(%esp)
	leal	LC1-"L00000000008$pb"(%ebx), %eax
	movl	%eax, 8(%esp)
	movl	-16(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	movl	%eax, -12(%ebp)
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$2, %eax
	jne	L109
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	$-2, (%eax)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	jmp	L112
L109:
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	cmpl	$1, %eax
	jne	L112
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	$-2, (%eax)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
	leal	_serial-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, (%esp)
	call	L_fclose$stub
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	$0, (%eax)
	movl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	244(%eax), %edx
	leal	_state-"L00000000008$pb"(%ebx), %eax
	movl	(%eax), %eax
	movl	%eax, 12(%esp)
	movl	-12(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	*%edx
L112:
	addl	$36, %esp
	popl	%ebx
	leave
	ret
.globl _Java_DeviceConnection_getState
_Java_DeviceConnection_getState:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$8, %esp
	call	L116
"L00000000009$pb":
L116:
	popl	%ecx
	leal	_state-"L00000000009$pb"(%ecx), %eax
	movl	(%eax), %eax
	leave
	ret
.lcomm _serial,4,2
.lcomm _state,4,2
	.section __IMPORT,__jump_table,symbol_stubs,self_modifying_code+pure_instructions,5
L_feof$stub:
	.indirect_symbol _feof
	hlt ; hlt ; hlt ; hlt ; hlt
L_fgets$stub:
	.indirect_symbol _fgets
	hlt ; hlt ; hlt ; hlt ; hlt
L_fopen$stub:
	.indirect_symbol _fopen
	hlt ; hlt ; hlt ; hlt ; hlt
L_fileno$stub:
	.indirect_symbol _fileno
	hlt ; hlt ; hlt ; hlt ; hlt
L_fputs$UNIX2003$stub:
	.indirect_symbol _fputs$UNIX2003
	hlt ; hlt ; hlt ; hlt ; hlt
L_tcsetattr$stub:
	.indirect_symbol _tcsetattr
	hlt ; hlt ; hlt ; hlt ; hlt
L_tcgetattr$stub:
	.indirect_symbol _tcgetattr
	hlt ; hlt ; hlt ; hlt ; hlt
L_cfsetospeed$stub:
	.indirect_symbol _cfsetospeed
	hlt ; hlt ; hlt ; hlt ; hlt
L_cfgetispeed$stub:
	.indirect_symbol _cfgetispeed
	hlt ; hlt ; hlt ; hlt ; hlt
L_cfsetispeed$stub:
	.indirect_symbol _cfsetispeed
	hlt ; hlt ; hlt ; hlt ; hlt
L_fclose$stub:
	.indirect_symbol _fclose
	hlt ; hlt ; hlt ; hlt ; hlt
	.subsections_via_symbols
