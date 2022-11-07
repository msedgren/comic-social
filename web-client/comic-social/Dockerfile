FROM node:18 as base-node

ENV USER node
ENV HOME /home/$USER

WORKDIR $HOME
USER $USER

FROM base-node as installed
COPY --chown=$USER package*.json ${HOME}/
RUN npm i --no-save
COPY --chown=$USER *.js *.json *.ts *.html ${HOME}/


FROM installed as development
ENTRYPOINT npm run dev

FROM installed as app
COPY --chown=$USER src ${HOME}/src
COPY --chown=$USER public ${HOME}/public
RUN npm run build

### production app ###
FROM nginx:stable-alpine as app-production
#log current date and time with timezone and add the user
RUN adduser -D -u 9999 app_user
# copy artifact build from the 'build environment'
COPY --from=app /home/node/dist /usr/share/nginx/html
#copy the nginx configuration with updated site (this helps with URL rewriting...) and restricted permissions back into place.
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./site.conf /etc/nginx/conf.d/default.conf
RUN chmod o+rx /etc/nginx/conf.d/default.conf
USER app_user

EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
