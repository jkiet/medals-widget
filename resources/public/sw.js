self.addEventListener('install', (event) => {
  console.log('# Service worker is installed! 🎉')
  event.waitUntil(
    caches.open('static').then((cache) => {
      cache.addAll([
        '/',
        '/css/widget.css',
        '/index.html',
        '/js/compiled/main.js',
      ])
    }),
  )
})

self.addEventListener('activate', () => {
  console.log('# Service worker is active! 🎉')
})

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((res) => {
      if (res) return res
      return fetch(event.request)
    }),
  )
})