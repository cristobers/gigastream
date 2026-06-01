# gigastream - NGINX RTMP Authentication

## Useful links

* https://github.com/Nesseref/nginx-rtmp-auth
* https://github.com/zorchenhimer/MovieNight/tree/master

## Key Generation

Keys are:

<ENDPOINT>_<RANDOM BYTES>

Example: `live_12804u21094rj1290ej019`
`head -c 1024 /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1`