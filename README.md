# dai-lab-http-infrastructure

## Step 1 - Static Web site

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

## Step 2 - Docker compose

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

## Step 3 - HTTP API server

------------

In this step, we implemented a simple HTTP API server using Javalin, a lightweight Java web framework. Our API manages a list of farm products, and each product has details such as name, price per kilogram, organic status, origin, and an image path.

### API Endpoints

### Fetch All Products
- **Endpoint**: `/api/products`
- **Method**: GET
- **Description**: Get the details of all farm products.

### Fetch a Single Product
- **Endpoint**: `/api/products/{productId}`
- **Method**: GET
- **Description**: Get the details of a specific farm product identified by its ID.

### Fetch Image of a Product
- **Endpoint**: `/api/products/image/{productId}`
- **Method**: GET
- **Description**: Get the image of a specific farm product identified by its ID.

### Add a New Product
- **Endpoint**: `/api/products`
- **Method**: POST
- **Description**: Add a new farm product. Provide the product details in the request body.

### Update a Product
- **Endpoint**: `/api/products`
- **Method**: PUT
- **Description**: Update the details of a specific farm product identified by its ID. Provide the updated details in the request body.

### Delete a Product
- **Endpoint**: `/api/products/{productId}`
- **Method**: DELETE
- **Description**: Delete a specific farm product identified by its ID.

## Data Model
We store farm product information in memory using a `ConcurrentHashMap`. Each product is represented by the `Product` class.

## Step 4 - Reverse proxy with Traefik

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


## Step 5 - Scalability and Load Balancing

------------

There are two ways to scale the application :

- Thanks to Traefik, directly modify the [Docker compose file](compose.yml) by adding the following lines to setup a fixed number of replicas, for example here 3:

```
#compose.yml
  static_web:
    ...
    deploy:
      replicas: 3
    ...
```

- Use the command:

```
docker-compose up -d --scale <service_name>=<number_of_instances>
```

### Check if everything is working

- Using the command:

```
docker-compose ps
```

INSERER CAPTURE D'ECRAN

- By using Traefik's Dashboard:

INSERER CAPTURE D'ECRAN

We can see that we have the correct number of replicas.

## Step 6 - Load balancing with round-robin and sticky sessions

------------

Add these lines under api service labels in docker compose :

- "traefik.http.services.api.loadBalancer.sticky.cookie=true"
- "traefik.http.services.api.loadBalancer.sticky.cookie.name=stickycookie"

## Step 7 - Securing Traefik with HTTPS

------------
