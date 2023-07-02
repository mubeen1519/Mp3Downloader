import os

import yt_dlp
import json

callbackGlobal = None


def my_hook(d):
    if d['status'] == 'downloading':
        out = {
            'downloaded_bytes': d['downloaded_bytes'],
            'total_bytes_estimate': d.get('total_bytes_estimate', 0),
            'eta': d.get('eta') if 'eta' in d else None,
            'speed': d.get('speed') if 'speed' in d else None
        }
        callbackGlobal(json.dumps(out))


ydl_opts = {
    'format': 'm4a/bestaudio/best',
    'outtmpl': os.path.join(os.path.expanduser('~'), '%(title)s.%(ext)s'),
    'progress_hooks': [my_hook],
}


def get_video_info(url):
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        info = ydl.extract_info(url, download=False)
        out = ydl.sanitize_info(info)
        result = {
            'title': out['title'],
            'thumbnail': out['thumbnail'],
            'view_count': out['view_count'],
            'like_count': out['like_count'],
        }
        return json.dumps(result)


def download_audio(url, callback=None):
    global callbackGlobal
    callbackGlobal = callback
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.add_progress_hook(my_hook)
        ydl.download(url)
