#!groovy

/* Created by: Abhishek Ranjan */

def call(def base) {
        this_base = base
    def glob_objs = this_base.get_glob_objs()
    def output = [
        'response': 'error',
        'message': ''
    ]

    /* Validate and sanitize the input */
    def result = this.input_validation()

    if (result['response'] == 'error') {
        return input_validation
    }

    def get_servers = ''
    /* find the server that scripts need to run against 
    def vcenters = ['mg20-vcsa1-001.core.cvent.org']
    def list_of_vms = ''

    for (Integer i = 0 ; i < vcenters.size(); i++) {
        get_servers = this_base.run_vmwarecli(
            'Getting luist of all virtual machines in vCenter',
            "(get-vm).name -split '`n' | %{\$_.trim()}",
            vcenters[i],
            [:]

        )
        if(get_servers['response'] == 'error') {
            return get_servers
        }

        if (i == 0) {
            list_of_vms = get_servers['message'].split('\r\n')
        } else{
            list_of_vms += get_servers['message'].split('\r\n')
        }
    }

    list_of_servers = []

    for(Integer i = 0; i < list_of_vms.size(); i++) {
        if (list_of_vms[i].contains(wf_region + '-wiz')) {
            list_of_servers += list_of_vms[i]
        }
    }

    */

    def list_of_servers = ['bh20-cpslin-046']

    /* Read bash script to stop the wiz services */
    def stop_services_script = base.read_wf_file('sys-linux-update-services-wiz', 'stop_wiz_services.sh')
    if(stop_services_script['response'] == 'error') {
        return stop_services_script
    }

    stop_services_script = stop_services_script['message']

    def stop_script = base.run_shellscript(
        "stopping all wiz services on '${list_of_servers[0]}'",
        stop_services_script,
        base.get_cred_id('bh20-cpslin-046'),
        [
            '_address_' : list_of_servers[0]
        ]
    )
     if(stop_script['response'] == 'error'){
         return stop_script
     }

     output['response'] = 'ok'
     output['message'] = stop_script['message']
        
    return output
}

def input_validation() {
    def output = [
        'response': 'ok',
        'message': ''
    ]

    /* The following is an example of how to validate a job parameter named 'wf_address'

    wf_address = wf_address.replaceAll('\\s', '').toLowerCase()
    this_base.set_str_param('wf_address', wf_address)

    if (wf_region == '') {
        output['message'] = 'Missing required parameter wf_address'

        return output
    }

    output['response'] = 'ok'

    */

    return output
}

return this
