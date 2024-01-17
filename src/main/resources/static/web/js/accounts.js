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
            current:""
            
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
                        console.log(response)
                    }).catch(e => console.log(e))
                }else{
                    axios.post("/api/clients/current/accounts?accountType=CURRENT")
                    .then(response => { 
                    console.log(response)
                    }).catch(e => console.log(e))

                }
              });
        },
        deleteAccount(){
            axios.patch("/api/accounts/" + this.id)
            .then(response => { this.card=false
                console.log(response)
            }).catch(e => console.log(e))
        }
    }

})

app.mount("#app")