# stop_wiz_services.sh
# Ranjan, Abhishek

# This script will run on a Jenkins Linux slave and returns the NTP offset.

cat <<EOF > remote.sh
# Set the PATH and TERM because piping a script to sshpass via STDIN does not initialize a terminal
export PATH=$PATH:/usr/local/sbin:/usr/sbin:/sbin
export TERM=xterm

sudo initctl list| grep -v tty | awk '{print $1}' > /tmp/services.txt
echo `sudo cat /tmp/services.txt`
#sudo cat services.txt
#failed_service=()
#while read line; do
#initctl stop $line
#serv=`initctl status $line`
#        if [[ $serv != *"start"* ]]; then
#                       failed_service+=("$line")
#         fi
#done < "services.txt"
#echo "List of all failed service"$'\n'"$failed_service"


EOF
 
# Execute the script on the remote Linux machine
sshpass -p "$__password__" ssh -o StrictHostKeyChecking=no $__username__@$_address_ < remote.sh