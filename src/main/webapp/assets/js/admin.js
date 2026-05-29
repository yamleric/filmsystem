document.addEventListener('click', function (event) {
    var target = event.target.closest('[data-confirm]');
    if (!target) {
        return;
    }
    var message = target.getAttribute('data-confirm') || '确认执行该操作？';
    if (!window.confirm(message)) {
        event.preventDefault();
    }
});

function replaceFailedMedia(target) {
    if (!target || !target.matches) {
        return;
    }
    if (target.matches('img[data-fallback]')) {
        if (target.dataset.replaced === 'true') {
            return;
        }
        target.dataset.replaced = 'true';
        var imageFallback = document.createElement('span');
        imageFallback.className = target.className + ' media-missing';
        imageFallback.textContent = target.getAttribute('data-fallback') || '文件缺失';
        target.parentNode.replaceChild(imageFallback, target);
        return;
    }
    if (target.matches('video[data-fallback]')) {
        if (target.dataset.replaced === 'true') {
            return;
        }
        target.dataset.replaced = 'true';
        var videoFallback = document.createElement('div');
        videoFallback.className = 'video-missing';
        videoFallback.textContent = target.getAttribute('data-fallback') || '影片文件缺失或无法预览';
        target.parentNode.replaceChild(videoFallback, target);
    }
}

document.addEventListener('error', function (event) {
    replaceFailedMedia(event.target);
}, true);

function scanFailedMedia() {
    var images = document.querySelectorAll('img[data-fallback]');
    for (var i = 0; i < images.length; i += 1) {
        if (images[i].complete && images[i].naturalWidth === 0) {
            replaceFailedMedia(images[i]);
        }
    }
    var videos = document.querySelectorAll('video[data-fallback]');
    for (var j = 0; j < videos.length; j += 1) {
        if (videos[j].error) {
            replaceFailedMedia(videos[j]);
        }
    }
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', scanFailedMedia);
} else {
    scanFailedMedia();
}
window.addEventListener('load', scanFailedMedia);
window.setTimeout(scanFailedMedia, 800);
