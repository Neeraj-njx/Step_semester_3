class CineScreen {
    private int seatsTotal;
    private int seatsAvailable;

    CineScreen(int seatsTotal) {
        if (seatsTotal <= 0) {
            throw new IllegalArgumentException("seatsTotal must be positive");
        }

        this.seatsTotal = seatsTotal;
        this.seatsAvailable = seatsTotal;
    }

    void bookSeat() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
        }
    }

    void cancelBooking() {
        if (seatsAvailable < seatsTotal) {
            seatsAvailable++;
        }
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }
}
