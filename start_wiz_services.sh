#start_wiz_services.sh
#Created by Abhishek Ranjan
#This script will run on a Jenkins Linux slave and start all the wiz services.


cat <<EOF > remote.sh
# Set the PATH and TERM because piping a script to sshpass via STDIN does not initialize a terminal
export PATH=$PATH:/usr/local/sbin:/usr/sbin:/sbin
export TERM=xterm
 
sudo cat services.txt
failed_service=()
while read line ;do
initctl start $line
serv=`initctl status $line`
		if [[ $serv != *"start"* ]]; then
			failed_service+=("$line")
		fi
done < "services.txt"
echo "List of all failed services"$'\n'"$failed_service"
 
EOF
 
# Execute the script on the remote Linux machine
sshpass -p "$__password__" ssh -o StrictHostKeyChecking=no $__username__@$_address_ < remote.sh