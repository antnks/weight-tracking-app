#!/bin/bash

DROPBOX_TOKEN="your_token"
DROPBOX_FILE="/your_dropbox_dir/your_pic.png"
CSV_URL="https://server/file.csv"

wget -q "$CSV_URL" -O file.csv
python3 csv2png.py file.csv "kg" file.png group_by_date

curl -s -X POST https://content.dropboxapi.com/2/files/upload --header "Authorization: Bearer $DROPBOX_TOKEN" \
	--header "Dropbox-API-Arg: {\"path\": \"$DROPBOX_FILE\", \"mode\": \"overwrite\"}" \
	--header "Content-Type: application/octet-stream" --data-binary "@file.png" > /dev/null

