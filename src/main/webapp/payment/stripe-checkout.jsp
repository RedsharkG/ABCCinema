<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - ABC Cinema</title>
    <link href="../css/payment.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://js.stripe.com/v3/"></script>
</head>
<body class="bg-dark text-white">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card payment-card">
                    <div class="card-body">
                        <h3 class="text-center mb-4">Complete Your Payment</h3>
                        <form id="payment-form">
                            <div id="payment-element" class="mb-3"></div>
                            <div id="payment-message" class="alert alert-danger d-none"></div>
                            <button id="submit" class="btn btn-primary w-100">
                                <span id="button-text">Pay Now</span>
                                <span id="spinner" class="spinner-border spinner-border-sm d-none"></span>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        const userId = sessionStorage.getItem('userId');
        if (!userId) {
            window.location.href = '${pageContext.request.contextPath}/user/login.jsp';
            return;
        }

        const stripe = Stripe('${stripePublicKey}');
        const elements = stripe.elements();
        const paymentElement = elements.create('payment');
        paymentElement.mount('#payment-element');

        const form = document.getElementById('payment-form');
        const submitButton = document.getElementById('submit');
        const spinner = document.getElementById('spinner');
        const messageDiv = document.getElementById('payment-message');

        form.addEventListener('submit', async (event) => {
            event.preventDefault();
            setLoading(true);
            
            try {
                const {error} = await stripe.confirmPayment({
                    elements,
                    confirmParams: {
                        return_url: '${pageContext.request.contextPath}/payment/payment-success.jsp',
                        payment_method_data: {
                            billing_details: {
                                email: '${sessionScope.user.email}'
                            }
                        }
                    }
                });

                if (error) {
                    showMessage(error.message);
                }
            } catch (e) {
                showMessage("An unexpected error occurred.");
            }
            
            setLoading(false);
        });

        function setLoading(isLoading) {
            submitButton.disabled = isLoading;
            spinner.classList.toggle('d-none', !isLoading);
            document.getElementById('button-text').style.display = isLoading ? 'none' : 'block';
        }

        function showMessage(messageText) {
            messageDiv.classList.remove('d-none');
            messageDiv.textContent = messageText;
            setTimeout(() => {
                messageDiv.classList.add('d-none');
            }, 4000);
        }
    </script>
</body>
</html>
