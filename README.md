# 🌸 Majestic Flower

**Decompilador Java Analítico de Alta Performance**

Um fork do projeto Fernflower mantido pela **Majestic World Studio** com melhorias e otimizações adicionais.

---

## 📖 Sobre o Projeto

Majestic Flower é baseado no **Fernflower**. Este fork inclui:

- ✅ **Melhor tratamento de erros** e logs detalhados
- ✅ **Novo modo de renomeação** (`-ren=2`) sem comentários
- ✅ **Criação automática** de diretórios de saída
- ✅ **Interface otimizada** para linha de comando
- ✅ **Documentação completa** em português brasileiro

## 🚀 Uso Rápido

### Comando Básico
```bash
java -jar majestic-flower.jar arquivo.jar diretorio_saida/
```

### Renomeação de Identificadores
```bash
# Com comentários (padrão)
java -jar majestic-flower.jar -ren=1 arquivo.jar saida/

# Sem comentários (novo)
java -jar majestic-flower.jar -ren=2 arquivo.jar saida/
```

## 📚 Documentação Completa

**👉 [Ver Documentação Detalhada](https://majesticworldstudio.github.io/majestic-flower/)**

A documentação completa inclui:
- Guia de instalação
- Exemplos práticos
- Lista completa de opções
- Comparações visuais dos modos de renomeação
- Casos de uso avançados

## 💾 Download

Baixe a versão mais recente do `majestic-flower.jar` na seção [Releases](../../releases).

## 🏗️ Instalação

### Compilar o projeto
```bash
./gradlew build
```

### Gerar executável
```bash
./gradlew installDist
```

O script será gerado em `build/install/engine/bin/`.

## 📝 Créditos e Licenças

### Projeto Original
- **Fernflower**: Desenvolvido pela [JetBrains](https://github.com/JetBrains/intellij-community/tree/master/plugins/java-decompiler/engine)
- **ForgeFlower**: Contribuições da [Minecraft Forge](https://github.com/MinecraftForge/ForgeFlower)

### Este Fork
- **Majestic World Studio**: Melhorias, otimizações e documentação
- **Licença**: [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

---

**Desenvolvido por Majestic World Studio**