// Utilitário para atualizar favicon
export const updateFavicon = () => {
  const favicon = document.querySelector('link[rel="icon"]') || document.createElement('link')
  favicon.rel = 'icon'
  favicon.type = 'image/png'
  favicon.href = '/logo.png'
  if (!document.querySelector('link[rel="icon"]')) {
    document.head.appendChild(favicon)
  }
}