const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            data: [],
            accounts: [],
            id:1,
            loans: [],
            input: false,
            selectButton: true,
            modal: false,
            savings: "",
            current:"",
            isMenuOpen: true
            
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current")
            .then(response => { 
                this.data = response.data
                this.accounts = this.data.accounts
                this.loans = response.data.loans
                console.log(this.data)
                console.log(this.accounts)
            })
            .catch(e => console.log(e))
        },
        logout(){
            axios.post("/api/logout")
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/index.html"
            })
            
        },
        createAccount(){
            Swal.fire({
                title: "Choose the best option to save your money",
                text: "and be part of this family",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#E6A51D",
                cancelButtonColor: "#E6A51D",
                confirmButtonText: "SAVINGS",
                cancelButtonText: "CURRENT"
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/clients/current/accounts?accountType=SAVINGS")
                    .then(response => { 
                        this.loadData()
                        console.log(response)
                    }).catch(e => console.log(e))
                }else{
                    axios.post("/api/clients/current/accounts?accountType=CURRENT")
                    .then(response => { 
                        this.loadData()
                    console.log(response)
                    }).catch(e => console.log(e))

                }
              });
        },
        deleteAccount(accountId){
            axios.patch("/api/accounts/" + accountId)
            .then(response => 
                console.log(response)
            ).catch(e => console.log(e))
        },
        ontoggleMenu(){
            this.isMenuOpen = !this.isMenuOpen
        },
        desktopMenu(){
            this.isMenuOpen = true
        },
        showInput(){
            this.input=true
        }
    }

})

app.mount("#app")