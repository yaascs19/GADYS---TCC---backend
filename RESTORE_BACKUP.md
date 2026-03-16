# 🔄 COMO RESTAURAR O BACKUP

Se algo der errado com a organização em pastas, execute:

```bash
# Voltar para a estrutura original
rm -rf public
mv public_backup public
```

## 📁 Nova Estrutura:
- `images/monumentos/` - Imagens de monumentos
- `images/natureza/` - Imagens de natureza  
- `images/gastronomia/` - Imagens de comidas
- `images/cultura/` - Imagens culturais
- `images/logos/` - Logos do site
- `images/geral/` - Outras imagens
- `pages/amazonas/` - Páginas do Amazonas
- `pages/geral/` - Páginas gerais
- `styles/` - Arquivos CSS
- `scripts/` - Arquivos JavaScript

## ⚠️ Caminhos Atualizados:
- Logo: `/images/logos/logo.png`
- Monumentos: `/images/monumentos/nome.jpg`
- Natureza: `/images/natureza/nome.jpg`
- etc.