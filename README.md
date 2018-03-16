Very, very simple bot, was made just for fun. 

# Main Functions
* call devops team

![](/readme/devops.png)

* get current weather in Saint-Petersburg

![](/readme/weather.png)

# How to run?

```bash
docker run \
    -d \
    -e "SLACK_TOKEN=token" \ # slack bot user OAuth access token
    -e "WEATHER_TOKEN=token"  \ # token (https://www.apixu.com) to get current weather in Saint-Petersburg
    -e "JIRA_URL=url" \ # link to Jira, may set it as localhost, if you don't have it
    --name superbot \
    azatarslanov/superbot
```
