# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  config.vm.box = "centos/7"
  # PostgreSQL(DBサーバ)
  config.vm.network "forwarded_port", guest: 5432, host:5432
  # Nginx(Webサーバ)
  config.vm.network "forwarded_port", guest: 80, host:80

  config.vm.network "private_network", ip:"172.16.0.20"
  config.vm.hostname = "firewall.local"
  config.ssh.forward_agent = true
  config.vm.synced_folder ".", "/vagrant", mount_options: ["dmode=777","fmode=777"], type:"virtualbox"

  config.vm.provision :shell, inline: <<-SHELL
    # Basic Packages Install
    yum update
    # Docker Compose Install
    curl -L "https://github.com/docker/compose/releases/download/v2.20.1/docker-compose-$(uname -s)-$(uname -m)" \
         -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
  SHELL

  config.vm.provision "docker" do |docker|
  end

  config.vm.provision :shell, run: "always", inline: <<-SHELL
    /usr/local/bin/docker-compose -f /vagrant/docker-compose.yml up -d
  SHELL
end
