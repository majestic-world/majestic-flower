# 🔧 Opções de Renaming do Fernflower

Esta documentação explica as diferentes opções de renaming disponíveis no Fernflower.

## 📋 Opções Disponíveis

### `-ren=0` (Padrão)
- **Descrição**: Sem renaming
- **Comportamento**: Mantém os nomes originais dos campos, métodos e classes
- **Uso**: Quando você quer preservar os nomes originais

```bash
java -jar fernflower.jar -ren=0 input.jar output/
```

### `-ren=1` (Renaming com Comentários)
- **Descrição**: Ativa o renaming e inclui comentários mostrando os nomes originais
- **Comportamento**: Renomeia identificadores obfuscados e adiciona comentários `// $FF: renamed from:`
- **Uso**: Útil para debug e entender a estrutura original

```bash
java -jar fernflower.jar -ren=1 input.jar output/
```

**Exemplo de saída com `-ren=1`:**
```java
public class MyClass {
   // $FF: renamed from: gs int
   private int field_2687;
   
   // $FF: renamed from: bJ boolean
   private boolean field_2688;
   
   // $FF: renamed from: a (Ljava/lang/String;)V
   public void method_1234(String param) {
      // código...
   }
}
```

### `-ren=2` (Renaming sem Comentários) ⭐ **NOVO**
- **Descrição**: Ativa o renaming mas **remove todos os comentários** de renaming
- **Comportamento**: Renomeia identificadores obfuscados mas não adiciona comentários `// $FF:`
- **Uso**: Para código limpo sem poluição visual dos comentários

```bash
java -jar fernflower.jar -ren=2 input.jar output/
```

**Exemplo de saída com `-ren=2`:**
```java
public class MyClass {
   private int field_2687;
   private boolean field_2688;
   
   public void method_1234(String param) {
      // código...
   }
}
```

## 🎯 Comparação Visual

| Opção | Renaming | Comentários $FF | Código Limpo |
|-------|----------|-----------------|--------------|
| `-ren=0` | ❌ | ❌ | ✅ |
| `-ren=1` | ✅ | ✅ | ❌ |
| `-ren=2` | ✅ | ❌ | ✅ |

## 💡 Casos de Uso

### Use `-ren=1` quando:
- ✅ Você está debugando código obfuscado
- ✅ Precisa entender a correspondência entre nomes originais e renomeados
- ✅ Está fazendo análise reversa detalhada

### Use `-ren=2` quando:
- ✅ Você quer código limpo e legível
- ✅ Não precisa dos nomes originais
- ✅ Está integrando o código descompilado em um projeto
- ✅ Quer remover a "poluição visual" dos comentários

## 🚀 Exemplo Completo

```bash
# Descompilação com comentários (para debug)
java -jar fernflower.jar -ren=1 l2gameserver.jar output_with_comments/

# Descompilação limpa (para uso)
java -jar fernflower.jar -ren=2 l2gameserver.jar output_clean/
```

## ⚙️ Implementação Técnica

A nova opção `-ren=2` funciona através de:

1. **Ativação do Renaming**: Como `-ren=1`, ativa o `IdentifierConverter`
2. **Desativação de Comentários**: Define `SHOW_RENAME_COMMENTS=0` internamente
3. **Processamento Limpo**: O `ClassWriter` verifica essa flag antes de adicionar comentários

```java
// Código interno - verificação antes de adicionar comentários
if (interceptor != null && DecompilerContext.getOption(IFernflowerPreferences.SHOW_RENAME_COMMENTS)) {
    String oldName = interceptor.getOldName(cl.qualifiedName);
    appendRenameComment(buffer, oldName, MType.CLASS, indent);
}
```
