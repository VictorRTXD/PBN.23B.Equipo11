	.file	"index2.c"
	.def	___main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
LC0:
	.ascii "%d\0"
LC1:
	.ascii " %d\0"
LC2:
	.ascii "hola mundo\0"
LC3:
	.ascii "adios mundo cruel\0"
	.text
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main: ;En este bloque inicia el main del programa 
LFB10: ;Este bloque da inicio al switch
	.cfi_startproc
	pushl	%ebp ;Operación de Transferencia de datos
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	movl	%esp, %ebp ;Operación de Transferencia de datos
	.cfi_def_cfa_register 5
	andl	$-16, %esp ;Operación Lógica
	subl	$32, %esp ;Operación Aritmética
	call	___main
	movl	$0, 20(%esp) ;Operación de Transferencia de datos
	movl	$0, 16(%esp) ;Operación de Transferencia de datos 
	movl	$0, 28(%esp) ;Operación de Transferencia de datos
	leal	24(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	movl	24(%esp), %eax ;Operación de Transferencia de datos
	cmpl	$5, %eax
	ja	L2 ;Operación de Control de flujo
	movl	L4(,%eax,4), %eax ;Operación de Transferencia de datos
	jmp	*%eax ;Operación de Control de flujo
	.section .rdata,"dr"
	.align 4
L4: ; opciones del switch
	.long	L2
	.long	L3
	.long	L5
	.long	L6
	.long	L7
	.long	L8
	.text
L3: ;primer caso o case 1 del switch
	leal	20(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	leal	16(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	movl	20(%esp), %edx ;Operación de Transferencia de datos
	movl	16(%esp), %eax ;Operación de Transferencia de datos
	addl	%edx, %eax ;Operación Aritmética
	movl	%eax, 28(%esp) ;Operación de Transferencia de datos
	movl	28(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_printf
	jmp	L9 ;Operación de Control de flujo
L5: ;segundo caso o case 2 del switch
	leal	20(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	leal	16(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	movl	20(%esp), %edx ;Operación de Transferencia de datos
	movl	16(%esp), %eax ;Operación de Transferencia de datos
	subl	%eax, %edx ;Operación Aritmética
	movl	%edx, %eax ;Operación de Transferencia de datos
	movl	%eax, 28(%esp) ;Operación de Transferencia de datos
	movl	28(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_printf
	jmp	L9 ;Operación de Control de flujo
L6: ;caso 3 del switch
	leal	20(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	leal	16(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	movl	20(%esp), %edx ;Operación de Transferencia de datos
	movl	16(%esp), %eax ;Operación de Transferencia de datos
	imull	%edx, %eax ;Operación Aritmética
	movl	%eax, 28(%esp) ;Operación de Transferencia de datos
	movl	28(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC1, (%esp) ;Operación de Transferencia de datos
	call	_printf
	jmp	L9 ;Operación de Control de flujo
L7: ;cuarto caso o case 4 del switch
	leal	20(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	leal	16(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC0, (%esp) ;Operación de Transferencia de datos
	call	_scanf
	movl	20(%esp), %eax ;Operación de Transferencia de datos
	movl	16(%esp), %ecx ;Operación de Transferencia de datos
	cltd
	idivl	%ecx ;Operación Aritmética
	movl	%eax, 28(%esp) ;Operación de Transferencia de datos
	movl	28(%esp), %eax ;Operación de Transferencia de datos
	movl	%eax, 4(%esp) ;Operación de Transferencia de datos
	movl	$LC1, (%esp) ;Operación de Transferencia de datos
	call	_printf
	jmp	L9 ;Operación de Control de flujo
L8: ;quinto caso o case 5 del switch
	movl	$LC2, (%esp) ;Operación de Transferencia de datos
	call	_printf
	jmp	L9 ;Operación de control de flujo
L2: ;caso default del switch
	movl	$LC3, (%esp) ;Operación de Transferencia de datos
	call	_printf
L9: ;aqui se da por terminado el switch
	movl	$0, %eax ;Operación de Transferencia de datos
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE10: ;aqui se da por terminado el main
	.ident	"GCC: (MinGW.org GCC-6.3.0-1) 6.3.0"
	.def	_scanf;	.scl	2;	.type	32;	.endef
	.def	_printf;	.scl	2;	.type	32;	.endef
