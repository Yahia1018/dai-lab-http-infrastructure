# dai-lab-http-infrastructure

## Step 1

------------

Here is our Dockerfile configuration:

```dockerfile
FROM nginx:latest
COPY ./static_website /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
```

- The 1st line gets the latest nginx image
- The 2nd line copies the website's content into the nginx specific folder for html
- The last replaces the default nginx configuration file with our own.

```
server {
    listen 80;  # HTTP port

    location / {
        root /usr/share/nginx/html;  # Default Nginx HTML directory
        
        index index.html;
    }
}
```

- `listen` defines the port on which the server listens
- The block `location /` indicates 


To use the website, run the following command in the directory containing the website folder, nginx.conf file, and the dockerfile :

```
$ docker build -t webserver .
```

And then :

```
$ docker run -d -p 8080:80 webserver
```

The website will then be running on localhost:8080

## Step 2

------------

We have created a file named compose.yml in the same directory as the previous files and its contents are as following :

```
services:

  static_web:
    image: webserver
    build: .
    ports:
      - "8080:80"

```

- `image` defines the image to use
- `build` build the dockerfile
- `ports` defines the ports to listen on for the local machine and the webserver

## Step 4

------------

Docker compose :

```
services:

  static_web:
    #image: webserver
    build: ./site
    #ports:
    #  - "8000:80"
    expose:
      - "80"
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.static.rule=Host(`localhost`)"
      #- "traefik.http.services.static.loadbalancer.server.port=80"

  api:
    #image: api-dependencies
    build: ./api
    expose:
      - "7000"
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.api.rule=Host(`localhost`) && PathPrefix(`/api`)"      
      #- "traefik.http.services.api.loadbalancer.server.port=7000"

#dashboard : http://localhost:8080/dashboard#/
  reverse-proxy:
    image: traefik:v2.10
    command: --api.insecure=true --providers.docker
    ports:
      - "80:80"
      - "8080:8080"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock  

```

## Step 5

------------

Added to docker compose under labels :
- "traefik.http.services.static.loadbalancer.server.port=80"
- "traefik.http.services.api.loadbalancer.server.port=7000"


## Step 6

------------

Add these lines under api service labels in docker compose :

- "traefik.http.services.api.loadBalancer.sticky.cookie=true"
- "traefik.http.services.api.loadBalancer.sticky.cookie.name=stickycookie"