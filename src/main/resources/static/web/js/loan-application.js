const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            accounts: [],
            destinationAccount: "",
            amount: 0,
            e : "",
            loanId: 0,
            payments: 0,
            loans:[],
            selectedAccount: 0,
            selectedPayments: 0,
           
        }
    },
    created(){
       this.loadData()
       this.loadLoans()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current/accounts")
            .then(response => { 
                this.accounts = response.data
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
        loadLoans(){
            axios.get("/api/loans")
            .then(response => {
                this.loans = response.data
                console.log(this.loans)
            }).catch(error => error)
        },
        findPayments(){
            const pay = this.loans.find(loan => loan.loanId == this.loanId)
            this.payments= pay.payments
            console.log(this.payments)
        },
        
        createTransaction(){
            
            Swal.fire({
                title: "Are you sure?",
                text: "Verify that all data is correct!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#E6A51D",
                cancelButtonColor: "#000000",
                confirmButtonText: "Yes, transfer!"
              }).then((result) => {
                if (result.isConfirmed) {
                    const body = {
                        "loanId": this.loanId,
                        "amount" : this.amount,
                        "payments" : this.selectedPayments,
                        "number" : this.selectedAccount
                    }
                    axios.post("/api/loans", body)
                    .then(result => {Swal.fire({
                            title: "Successfull Transfer!",
                            text: "",
                            icon: "success",
                            confirmButtonColor: "#E6A51D",
                          }).then((result) => {
                            if (result.isConfirmed){
                                window.location.href = "/web/html/accounts.html"
                            }
                          })
                        })
                    .catch(error => {Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: this.e = error.response.data,
                        confirmButtonColor: "#E6A51D"
                      });
                    console.log(error)
                    })
                }
              })
        }
       

    }

})

app.mount("#app")